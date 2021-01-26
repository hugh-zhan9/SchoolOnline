package com.hugh.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by hugh on 2021/1/14
 */
@Service
public interface VideoManagerService {
    String uploadVideo(MultipartFile file);

    void deleteVideoById(String id) throws ClientException;

    void deleteAllVideo(List<String> videoIdList) throws ClientException;

    String getPlayAuth(String id) throws ClientException;
}
