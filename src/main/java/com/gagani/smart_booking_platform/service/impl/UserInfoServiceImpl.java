package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.request.UserRequestDTO;
import com.gagani.smart_booking_platform.dto.response.UserResponseDTO;
import com.gagani.smart_booking_platform.entity.Organization;
import com.gagani.smart_booking_platform.entity.Role;
import com.gagani.smart_booking_platform.entity.UserInfo;
import com.gagani.smart_booking_platform.entity.enums.UserStatus;
import com.gagani.smart_booking_platform.exception.DuplicateResourceException;
import com.gagani.smart_booking_platform.exception.ResourceNotFoundException;
import com.gagani.smart_booking_platform.repository.OrganizationRepository;
import com.gagani.smart_booking_platform.repository.RoleRepository;
import com.gagani.smart_booking_platform.repository.UserInfoRepository;
import com.gagani.smart_booking_platform.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService, UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;


    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, OrganizationRepository organizationRepository) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userInfoRepository.findUserByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserInfo user = new UserInfo();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated_at(Instant.now());
        user.setAddress(userRequestDTO.getAddress());
        user.setPhone(userRequestDTO.getPhone());

        Organization organization = organizationRepository
                .findById(userRequestDTO.getOrganization().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        user.setOrganization(organization);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default user role not found"));
        user.setRoles(Set.of(defaultRole));

        UserInfo userInfo =  userInfoRepository.save(user); // need to return saved entity

        return mapUserToDTO(userInfo);
    }

    @Override
    public UserResponseDTO updateUser(int id, UserRequestDTO user) {
        Optional<UserInfo> userOptional = userInfoRepository.findById(id);

        if(userOptional.isPresent()) {
            UserInfo userToUpdate = userOptional.get();
            userToUpdate.setName(user.getName());

            boolean exist = userInfoRepository.existsByEmailAndIdNot(user.getEmail(), (long) id);
            if (exist) {
                throw new DuplicateResourceException(String.format("User with email %s already exists", user.getEmail()));
            }

            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setUpdated_at(Instant.now());
            userToUpdate.setStatus(user.getStatus());
            userToUpdate.setOrganization(user.getOrganization());
            UserInfo userUpdateInfo = userInfoRepository.save(userToUpdate);

            return mapUserToDTO(userUpdateInfo);
        }
        else{
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public void deleteUser(int id) {

        Optional<UserInfo> userOptional = userInfoRepository.findById(id);
        if(userOptional.isPresent()) {
            userInfoRepository.delete(userOptional.get());
        }else{
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public UserResponseDTO getUserbyId(int id) {
        UserInfo user = userInfoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapUserToDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserInfo user = userInfoRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<UserInfo> userOptional = userInfoRepository.findAll(pageable);
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found!");
        }

        return userOptional.map(this::mapUserToDTO);
    }

    @Override
    public UserResponseDTO getCurrentUser(String email) {
        UserInfo currentUserInfo = userInfoRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return mapUserToDTO(currentUserInfo);
    }

    public UserResponseDTO mapUserToDTO(UserInfo userInfo) {

        UserResponseDTO userDTO = new UserResponseDTO();

        userDTO.setName(userInfo.getName());
        userDTO.setEmail(userInfo.getEmail());
        userDTO.setRole(userInfo.getRoles());
        userDTO.setStatus(userInfo.getStatus());
        userDTO.setAddress(userInfo.getAddress());
        userDTO.setPhone(userInfo.getPhone());
        userDTO.setOrganization(userInfo.getOrganization());

        return userDTO;
    }
}
