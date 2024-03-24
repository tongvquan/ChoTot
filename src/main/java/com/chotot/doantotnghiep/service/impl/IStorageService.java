package com.chotot.doantotnghiep.service.impl;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    void init();
    void store(MultipartFile file);
}
