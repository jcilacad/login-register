package com.ilacad.jc.mvcsecurityexercise.service;

import com.ilacad.jc.mvcsecurityexercise.dto.UserDto;
import com.ilacad.jc.mvcsecurityexercise.entity.Role;
import com.ilacad.jc.mvcsecurityexercise.entity.User;
import com.ilacad.jc.mvcsecurityexercise.repository.RoleRepository;
import com.ilacad.jc.mvcsecurityexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role;
        Optional<Role> result = roleRepository.findByName("ROLE_ADMIN");

        if (result.isPresent()) {
            role = result.get();
        } else {
            role = checkRoleExist();
            throw new RuntimeException("Did not find role with role name of - " + role.getName());
        }

        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {

        User user;
        Optional<User> result = userRepository.findByEmail(email);

        if (result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("Did not find user with email - " + email);
        }

        return user;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> mapUserToDto(user))
                .collect(Collectors.toList());

    }

    private UserDto mapUserToDto (User user) {

        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");

        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());

        return userDto;

    }

    private Role checkRoleExist() {

        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
