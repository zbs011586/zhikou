package com.zhikou.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.zhikou.code.bean.FullUserInfo;
import com.zhikou.code.bean.Shop;
import com.zhikou.code.bean.UserInfo;
import com.zhikou.code.bean.User;
import com.zhikou.code.commons.ApiBaseAction;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.commons.IgnoreAuth;
import com.zhikou.code.dao.ShopDao;
import com.zhikou.code.param.UserParam;
import com.zhikou.code.service.AccountService;
import com.zhikou.code.service.TokenService;
import com.zhikou.code.service.UserService;
import com.zhikou.code.utils.ApiUserUtils;
import com.zhikou.code.utils.CharUtil;
import com.zhikou.code.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController extends ApiBaseAction {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ShopDao shopDao;

    /**
     * @description 微信登录 参数为code和一个FullUserInfo对象
     * FullUserInfo对象的key为userInfo 以json的形式传递
     * @author 张宝帅
     * @date 2019/8/25 21:08
     */
    @IgnoreAuth
    @PostMapping("/wx/login")
    public ResponseEntity loginByWx() {
        JSONObject jsonParam = this.getJsonRequest();
        FullUserInfo fullUserInfo = null;
        String code = "";
        if (!StringUtils.isNullOrEmpty(jsonParam.getString("code"))) {
            code = jsonParam.getString("code");
        }
        if (null != jsonParam.get("userInfo")) {
            fullUserInfo = jsonParam.getObject("userInfo", FullUserInfo.class);
        }
        if (null == fullUserInfo) {
            return ResponseEntity.ok(HttpResponse.ERROR(Constants.ErrorCode.LOGIN_ERROR,"登录失败：userInfo信息不完整"));
        }

        Map<String, Object> resultObj = new HashMap();

        UserInfo userInfo = fullUserInfo.getUserInfo();

        //获取配置文件中的appId和secret 通过自定义工具类组合成requestUrl请求获得openid和session_key
        String requestUrl = ApiUserUtils.getWebAccess(code);
        log.info("requestUrl 为：" + requestUrl);
        JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null == sessionData || StringUtils.isNullOrEmpty(sessionData.getString("openid"))) {
            return ResponseEntity.ok(HttpResponse.ERROR(Constants.ErrorCode.LOGIN_ERROR,"登录失败：无法获取用户openid"));
        }
        /*//验证用户信息完整性
        String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
        if (!fullUserInfo.getSignature().equals(sha1)) {
            return ResponseEntity.ok(HttpResponse.ERROR(Constants.ErrorCode.LOGIN_ERROR,"登录失败：用户登录信息不完整"));
        }*/
        Date nowTime = new Date();
        User user = userService.queryByOpenId(sessionData.getString("openid"));
        if (null == user) {
            user = new User();
            user.setUsername("微信用户" + CharUtil.getRandomString(12));
            user.setPassword(sessionData.getString("openid"));
            user.setRegister_time(nowTime);
            user.setRegister_ip(this.getClientIp());
            user.setLast_login_ip(user.getRegister_ip());
            user.setLast_login_time(user.getRegister_time());
            user.setUser_role(0);
            user.setWeixin_openid(sessionData.getString("openid"));
            user.setAvatar(userInfo.getAvatarUrl());
            //性别 0：未知、1：男、2：女
            user.setGender(userInfo.getGender());
            user.setNickname(userInfo.getNickName());
            userService.save(user);
        } else {
            user.setLast_login_ip(this.getClientIp());
            user.setLast_login_time(nowTime);
            userService.update(user);
        }

        Map<String, Object> tokenMap = tokenService.createToken(user.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (null == userInfo || StringUtils.isNullOrEmpty(token)) {
            return ResponseEntity.ok(HttpResponse.ERROR(Constants.ErrorCode.LOGIN_ERROR,"登录失败：生成token异常"));
        }

        resultObj.put("token", token);
        resultObj.put("userId", user.getUserId());
        log.info(userInfo.getNickName()+"登录成功");
        return ResponseEntity.ok(HttpResponse.OK(resultObj));
    }

    /**
     * @description 申请成为商家
     * @author 张宝帅
     * @date 2019/9/2 17:36
     */
    @PostMapping("/shop/registry")
    public ResponseEntity shopRegistry(@RequestBody Shop param){
        return ResponseEntity.ok(accountService.shopRegistry(getUserId(),param));
    }

    /**
     * @description 获取用户的info信息
     * @author 张宝帅
     * @date 2019/9/2 18:08
     */
    @IgnoreAuth
    @PostMapping("/user/info")
    public ResponseEntity getUserInfo(@RequestBody UserParam param){
        return ResponseEntity.ok(accountService.getUserInfo(param.getGoalUserId()));
    }
}
