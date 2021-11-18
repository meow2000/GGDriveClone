package com.project.GGDriveClone;

import com.project.GGDriveClone.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class GgDriveCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(GgDriveCloneApplication.class, args);
	}

}
