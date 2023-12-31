package com.ilacad.jc.mvcsecurityexercise.service;

import com.ilacad.jc.mvcsecurityexercise.dto.UserDto;
import com.ilacad.jc.mvcsecurityexercise.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAll();

    boolean isUserExists(UserDto user);

    boolean isUserAuthenticated(Authentication authentication);
}
