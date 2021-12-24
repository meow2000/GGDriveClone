package com.project.GGDriveClone.convert;

import com.project.GGDriveClone.DTO.FileDto;
import com.project.GGDriveClone.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileConvert {
    public FileDto convertToFileDto (FileEntity fileEntity, String message){
        FileDto fileDto = new FileDto();
        fileDto.setId(fileEntity.getId());
        fileDto.setUid(fileEntity.getUid());
        fileDto.setName(fileEntity.getName());
        fileDto.setPath(fileEntity.getPath());
        fileDto.setCreatedTime(fileEntity.getCreatedTime());
        fileDto.setUpdatedTime(fileEntity.getUpdatedTime());
        fileDto.setSize(fileEntity.getSize());
        fileDto.setType(fileEntity.getType());
        fileDto.setMessage(message);
        return fileDto;
    }
}
