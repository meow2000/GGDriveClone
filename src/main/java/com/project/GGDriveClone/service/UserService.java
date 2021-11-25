package com.project.GGDriveClone.service;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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

    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(UserEntity user) {
        user.setCreated_time(new Timestamp(System.currentTimeMillis()));
        user.setUpdated_time(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
