package com.hugh.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugh on 2021/1/6
 */
@Data
public class ReturnMessage {

    private ReturnMessage(){};

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "请求返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<String, Object>();

    public static ReturnMessage ok(){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setSuccess(true);
        returnMessage.setCode(20000);
        returnMessage.setMessage("请求成功");
        return returnMessage;
    }

    public static ReturnMessage ng(){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setSuccess(false);
        returnMessage.setCode(20001);
        returnMessage.setMessage("请求失败");
        return returnMessage;
    }

    public static ReturnMessage pash(){
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setSuccess(true);
        returnMessage.setCode(25000);
        returnMessage.setMessage("支付中");
        return returnMessage;
    }

    public ReturnMessage success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ReturnMessage resultCode(Integer resultCode){
        this.setCode(resultCode);
        return this;
    }

    public ReturnMessage message(String message){
        this.setMessage(message);
        return this;
    }

    public ReturnMessage data(Map<String,Object> data){
        this.setData(data);
        return this;
    }

    public ReturnMessage data (String key, Object value){
        this.data.put(key,value);
        return this;
    }

}
