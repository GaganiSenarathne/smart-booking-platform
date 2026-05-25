package com.gagani.smart_booking_platform.dto.response;

import com.gagani.smart_booking_platform.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponseDTO {

    private Long id;
    private String name;
    private Set<UserInfo> userInfo;
}
