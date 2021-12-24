package com.project.GGDriveClone.convert;

import com.project.GGDriveClone.DTO.UserDto;
import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConvert {
    public UserDto convertToDto(UserEntity userEntity, String message){
        UserDto userDto = new UserDto();
        userDto.setEmail(userEntity.getEmail());
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setPassword(userEntity.getPassword());
        userDto.setStorage(userEntity.getStorage());
        userDto.setMessage(message);
        return userDto;
    }
}
