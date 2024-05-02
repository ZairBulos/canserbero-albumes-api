package com.zair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CanserberoAlbumesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanserberoAlbumesApiApplication.class, args);
	}

}
