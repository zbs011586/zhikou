package com.zhikou.code.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhikou.code.bean.Token;
import com.zhikou.code.interceptor.TokenInterceptor;
import com.zhikou.code.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * @description 基础控制类
 * @author 张宝帅
 * @date 2019/8/25 20:54
 */
@Slf4j
public class ApiBaseAction {
    /**
     * 得到request对象
     */
    @Autowired
    protected HttpServletRequest request;
    /**
     * 得到response对象
     */
    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected TokenService tokenService;

    /**
     * @description 获取请求方IP
     * @author 张宝帅
     * @date 2019/9/1 2:03
     */
    public String getClientIp() {
    	String xff = request.getHeader("X-Real-IP");
    	if(xff!=null) {
    		return xff;
    	}
        xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            return "8.8.8.8";
        }
        return xff;
    }

    /**
     * @description 处理请求的json参数
     * @author 张宝帅
     * @date 2019/9/1 2:28
     */
    public JSONObject getJsonRequest() {
        JSONObject result = null;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader();) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
            result = JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @description 获取请求方的userId
     * @author 张宝帅
     * @date 2019/9/1 2:03
     */
    public Integer getUserId() {
        System.out.println(request.getHeader(TokenInterceptor.USER_ID));
        String token = request.getHeader(TokenInterceptor.TOKEN);
        //查询token信息
        Token tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            return null;
        }
        return tokenEntity.getUserId();
    }
}
