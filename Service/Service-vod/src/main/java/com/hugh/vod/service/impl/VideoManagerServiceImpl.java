package com.hugh.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hugh.vod.service.VideoManagerService;
import com.hugh.vod.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugh on 2021/1/14
 */
@Service
public class VideoManagerServiceImpl implements VideoManagerService {

    @Override
    public String uploadVideo(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            // 流式上传
            UploadStreamRequest request = new UploadStreamRequest(PropertiesUtil.ACCESS_KEY_ID, PropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return response.getVideoId();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    @Override
    public void deleteVideoById(String id) throws ClientException {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId,PropertiesUtil.ACCESS_KEY_ID,PropertiesUtil.ACCESS_KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(id);
        DeleteVideoResponse response = client.getAcsResponse(request);
        System.out.println("RequestId:："+response.getRequestId());
    }

    @Override
    public void deleteAllVideo(List<String> videoIdList) throws ClientException {
        // 拼接ID
        String id = StringUtils.join(videoIdList,",");

        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId,PropertiesUtil.ACCESS_KEY_ID,PropertiesUtil.ACCESS_KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(id);
        DeleteVideoResponse response = client.getAcsResponse(request);
        System.out.println("RequestId:："+response.getRequestId());
    }

    @Override
    public String getPlayAuth(String id) throws ClientException {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId,PropertiesUtil.ACCESS_KEY_ID,PropertiesUtil.ACCESS_KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(id);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        // 播放凭证
        System.out.println("PlayAuth = "+ response.getPlayAuth());
        //VideoMeta信息 视频名称
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        return response.getPlayAuth();
    }


}
