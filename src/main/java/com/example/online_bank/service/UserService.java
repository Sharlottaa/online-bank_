package com.example.online_bank.service;

import com.example.online_bank.entity.User;
import java.util.List;

import com.example.online_bank.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}