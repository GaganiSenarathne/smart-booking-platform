package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.UserRequestDTO;
import com.gagani.smart_booking_platform.dto.UserResponseDTO;
import com.gagani.smart_booking_platform.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserResponseDTO createUser(UserRequestDTO user);
    UserResponseDTO updateUser(int id, UserInfo user);
    void deleteUser(int id);
    UserResponseDTO getUserbyId(int id);
//    User getUserbyEmail(String email);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getCurrentUser(String email);

}
