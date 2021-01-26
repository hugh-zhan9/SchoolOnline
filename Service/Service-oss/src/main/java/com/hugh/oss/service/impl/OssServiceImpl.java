package com.hugh.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hugh.oss.service.OssService;
import com.hugh.oss.utils.PropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by hugh on 2021/1/10
 */
@Service
public class OssServiceImpl implements OssService {

    /*
        上传文件到OSS
     */

    /**
     *
     * @param avatar
     * @return
     */
    @Override
    public String uploadAvatar(MultipartFile avatar) {

        String fileName = avatar.getOriginalFilename();
        // 添加随机值防止文件名称重复导致上传覆盖
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        // 按照上传日期创建文件夹分类管理
        String date = new DateTime().toString("yyyy/MM/dd");
        fileName = date+"/"+uuid+fileName;

        OSS ossClient = new OSSClientBuilder().build(PropertiesUtils.END_POINT,PropertiesUtils.ACCESS_KEY_ID,PropertiesUtils.ACCESS_KEY_SECRET);
        try {
            InputStream inputStream = avatar.getInputStream();
            /**
             *
             * @param buckName，阿里云中创建的buck的名称
             * @param fileName，文件存放的路径和名称
             * @param inputStream，文件输入流
             * @return
             */
            // 第二个参数为文件路径和名称
            ossClient.putObject(PropertiesUtils.BUCK_NAME, fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            String url = "https://"+PropertiesUtils.BUCK_NAME+"."+PropertiesUtils.END_POINT+"/"+fileName;
            ossClient.shutdown();
            return url;
        }
    }
}
