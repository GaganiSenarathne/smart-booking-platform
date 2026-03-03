package com.gagani.smart_booking_platform.repository;

import com.gagani.smart_booking_platform.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {


    Optional<UserInfo> findUserByEmail(String email);

    boolean existsByEmailAndIdNot(String email, int id);

    Optional<UserInfo> findByEmail(String username);
}
