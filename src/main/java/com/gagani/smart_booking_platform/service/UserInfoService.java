package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.request.UserRequestDTO;
import com.gagani.smart_booking_platform.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserInfoService {

    UserResponseDTO createUser(UserRequestDTO user);
    UserResponseDTO updateUser(int id, UserRequestDTO userRequestDTO);
    void deleteUser(int id);
    UserResponseDTO getUserbyId(int id);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    UserResponseDTO getCurrentUser(String email);

}
