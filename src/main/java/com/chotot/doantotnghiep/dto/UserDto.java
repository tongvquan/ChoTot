package com.chotot.doantotnghiep.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userName;
    private String password;
    private String address;
    private String phoneNumber;
}
