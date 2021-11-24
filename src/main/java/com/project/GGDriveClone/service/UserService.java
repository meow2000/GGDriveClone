package com.project.GGDriveClone.service;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findUser(String username){
        return userRepository.findUserEntityByName(username);
    }

    public UserEntity findUserbyEmail(String email){
        return userRepository.findUserEntityByName(email);
    }
}
