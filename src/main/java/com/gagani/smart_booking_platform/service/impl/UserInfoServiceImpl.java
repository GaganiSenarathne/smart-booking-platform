package com.gagani.smart_booking_platform.service.impl;

import com.gagani.smart_booking_platform.dto.UserDTO;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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
    public UserInfo createUser(UserDTO userDTO) {
        if (userInfoRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserInfo user = new UserInfo();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated_at(Instant.now());
        Organization organization = organizationRepository
                .findById(userDTO.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        user.setOrganization(organization);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));

        user.setRoles(Set.of(defaultRole));

        return userInfoRepository.save(user); // need to return saved entity
    }

    @Override
    public UserInfo updateUser(int id, UserInfo user) {

        Optional<UserInfo> userOptional = userInfoRepository.findById(id);

        if(userOptional.isPresent()) {
            UserInfo userToUpdate = userOptional.get();
            userToUpdate.setName(user.getName());

            boolean exist = userInfoRepository.existsByEmailAndIdNot(user.getEmail(), id);
            if (exist) {
                throw new DuplicateResourceException("User with email " + user.getEmail() + " already exists");
            }

            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setUpdated_at(Instant.now());
            userToUpdate.setStatus(user.getStatus());
            userToUpdate.setOrganization(user.getOrganization());
            return userInfoRepository.save(userToUpdate);
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
    public UserInfo getUserbyId(int id) {

        return userInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by the ID: " + id));
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
    public List<UserInfo> getAllUsers() {

        List<UserInfo> userOptional = userInfoRepository.findAll();
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return userOptional;
    }
}
