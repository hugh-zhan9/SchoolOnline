import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.aliyun.vod.upload.req.*;

import java.util.List;

/**
 * 阿里云上传视频测试类
 *
 * Created by hugh on 2021/1/13
 */
public class OssVodTest {

    private static final String accessKeyId = "LTAI4G1VZZE5tJKqbtetwrAy";
    private static final String accessKeySecret ="xgxnIXMprdszkxXGDlTFGqoPxX3Dxq";

    public static void main(String[] args) {
        System.out.println("调用方法");

        try {
            getVideoPlayAuth("cccf5eacf8b8428c9df309257e6081f9",accessKeyId,accessKeySecret);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化视频点播服务客户端
     * @param accessKeyId   OSS认证ID
     * @param accessKeySecret   OSS认证密码
     * @return
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret){
        // 点播服务接入地址
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取视频URL
     * @param videoId 视频ID
     * @return
     * @throws ClientException
     */
    public static String getPlayUrl(String videoId, String accessKeyId, String accessKeySecret) throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        GetPlayInfoResponse response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for(GetPlayInfoResponse.PlayInfo playInfo : playInfoList){
            System.out.println("播放地址："+playInfo.getPlayURL());
        }
        System.out.println("VideoBase.Title："+response.getVideoBase().getTitle());
        return playInfoList.get(0).getPlayURL();
    }

    /**
     * 获取视频凭证
     * @param videoId 视频ID
     * @return
     * @throws ClientException
     */
    public static String getVideoPlayAuth (String videoId, String accessKeyId, String accessKeySecret) throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        // 播放凭证
        System.out.println("PlayAuth = "+ response.getPlayAuth());
        //VideoMeta信息 视频名称
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        return response.getPlayAuth();
    }

    /**
     * 本地文件上传
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @Return 视频ID
     */
    private static String uploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return response.getVideoId();
    }

    /**
     * 通过指定ID删除视频
     * @param videoIds
     * @param accessKeyId
     * @param accessKeySecret
     * @throws ClientException
     */
    public static void deleteVideo(String videoIds, String accessKeyId, String accessKeySecret) throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoIds);
        DeleteVideoResponse response = client.getAcsResponse(request);
        System.out.println("RequestId:："+response.getRequestId());
    }
}
