package com.zhikou.code.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhikou.code.bean.Shop;
import com.zhikou.code.bean.User;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.ShopDao;
import com.zhikou.code.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

@Service
public class AccountService {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private UserDao userDao;


    public HttpResponse  getUserInfo(int userId){
        User user = new User();
        user.setUserId(userId);
        User one = userDao.selectOne(user);
        HashMap map = new HashMap();
        if (one.getUser_role() == 0){
            map.put("role",0);
            map.put("shopInfo",null);
        }else {
            Shop param = new Shop();
            param.setUserId(userId);
            Shop shop = shopDao.selectOne(param);
            map.put("role",1);
            map.put("shopInfo",shop);
        }
        map.put("userInfo",one);
        return HttpResponse.OK(map);
    }

    public HttpResponse shopRegistry(int userId,Shop param){
        //判断当前用户是否已经是商家
        Shop shop = new Shop();
        shop.setUserId(userId);
        Shop selectOne = shopDao.selectOne(shop);
        if (selectOne !=null ){
            return HttpResponse.ERROR(Constants.ErrorCode.REQUEST_ERROR,"已经是商家,不可重复入驻");
        }
        param.setUserId(userId);
        //计算经纬度
        String location = getLocation(param.getProvince() + param.getCity() + param.getDistrict() + param.getAddress());
        String[] lonLat = StringUtils.split(location, ",");
        Double lon = Double.valueOf(lonLat[0]);
        Double lat = Double.valueOf(lonLat[1]);
        param.setLon(lon);
        param.setLat(lat);
        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        shopDao.insert(param);
        //修改user表中user_role状态为1
        userDao.updateRole(userId,1);
        return HttpResponse.OK("入驻成功");
    }

    public String getLocation(String address){
        //调用高德api的逆地理编码接口 获取商家地址的经纬度信息
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.getForEntity("https://restapi.amap.com/v3/geocode/geo?address="+address+"&output=JSON&key=ebe1b5a862d0ffac954af5cfc9261d06", String.class);
        JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
        String location = jsonObject.getJSONArray("geocodes").getJSONObject(0).getString("location");
        return location;
    }

}
