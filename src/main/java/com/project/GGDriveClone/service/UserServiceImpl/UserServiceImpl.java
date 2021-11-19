package com.project.GGDriveClone.service.UserServiceImpl;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.UserRepository;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserEntity> getAllUser() {
    return (List<UserEntity>) userRepository.findAll();
  }

  @Override
  public void saveUser(UserEntity user) {
    userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public Optional<UserEntity> findUserById(Long id) {
    return userRepository.findById(id);
  }
}