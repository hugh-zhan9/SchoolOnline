package com.hugh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hugh on 2021/1/10
 */
public interface OssService {
    String uploadAvatar(MultipartFile avatar);
}
