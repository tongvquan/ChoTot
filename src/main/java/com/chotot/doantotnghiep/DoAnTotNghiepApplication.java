package com.chotot.doantotnghiep;

import com.chotot.doantotnghiep.service.StorageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoAnTotNghiepApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoAnTotNghiepApplication.class, args);
    }
    @Bean
    CommandLineRunner init(StorageService storageService){
        return (args -> {
//            storageService.deleteAll();
            storageService.init();
        });
    }


}
