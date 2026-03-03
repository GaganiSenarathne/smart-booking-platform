package com.gagani.smart_booking_platform.service;

import com.gagani.smart_booking_platform.dto.UserDTO;
import com.gagani.smart_booking_platform.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo createUser(UserDTO user);
    UserInfo updateUser(int id, UserInfo user);
    void deleteUser(int id);
    UserInfo getUserbyId(int id);
//    User getUserbyEmail(String email);
    List<UserInfo> getAllUsers();

}
