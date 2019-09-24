package com.zhikou.code.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhikou.code.annotation.IgnoreAuth;
import com.zhikou.code.bean.Token;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description 权限(Token)验证
 * @author 张宝帅
 * @date 2019/8/25 21:03
 */
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    public static final String TOKEN = "token";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject res = null;

        //静态资源放行
        if (request.getRequestURI().startsWith("/image")){
            System.out.println(request.getRequestURI());
            log.info(request.getRequestURI());
            return true;
        }

        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }
        //从header中获取token
        String token = request.getHeader(TOKEN);

        //token为空
        if (StringUtils.isBlank(token)) {
            res = new JSONObject();
            res.put("data","token为空");
            res.put("error_code",Constants.ErrorCode.TOKEN_ERROR);
            writerResponse(response,res);
            return false;
        }

        //查询token信息 此处不再做token的过期判断
        Token tokenEntry = tokenService.queryByToken(token);
        if (tokenEntry == null /*|| tokenEntry.getExpireTime().getTime() < System.currentTimeMillis()*/) {
            res = new JSONObject();
            res.put("data","token不存在");
            res.put("error_code",Constants.ErrorCode.TOKEN_ERROR);
            writerResponse(response,res);
            return false;
        }

        return true;
    }

    private void writerResponse(HttpServletResponse response,JSONObject res) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(res.toString());
        out.flush();
        out.close();
    }
}
