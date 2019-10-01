package com.zhikou.code.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;
import com.zhikou.code.bean.*;
import com.zhikou.code.commons.HttpResponse;
import com.zhikou.code.dao.*;
import com.zhikou.code.param.MessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserLikeDao userLikeDao;

    @Autowired
    private UserCommentDao userCommentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private UserScanDao userScanDao;

    @Autowired
    private UserWarnDao userWarnDao;

    @Autowired
    private AdviceDao adviceDao;

    @Autowired
    private AccountService accountService;

    public HttpResponse messageData(int userId,int type, int adcode, String classify, String inputText,
                                    int rebateOrder, double lon, double lat, int radius,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        double minLon = 0d;
        double maxLon = 0d;
        double minLat = 0d;
        double maxLat = 0d;
        if (lon != 0 && lat != 0 && radius != 0) {
            //计算筛选的经纬度范围
            SpatialContext geo = SpatialContext.GEO;
            Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(geo.makePoint(lon, lat), radius * DistanceUtils.KM_TO_DEG, geo, null);
            minLon = rectangle.getMinX();
            maxLon = rectangle.getMaxX();
            minLat = rectangle.getMinY();
            maxLat = rectangle.getMaxY();
        }

        if (type == 0){
            //查询消息
            List<Message> messages = messageDao.messageData(adcode, classify, inputText, rebateOrder, minLon, maxLon, minLat, maxLat);
            List<Message> list = handleMessage(messages, userId);
            //计算距离
            if (list.size() !=0){
                for (Message message : list) {
                    double messageLon =message.getLon();
                    double messageLat =message.getLat();
                    SpatialContext geo = SpatialContext.GEO;
                    double distance = geo.calcDistance(geo.makePoint(lon, lat), geo.makePoint(messageLon, messageLat)) * DistanceUtils.DEG_TO_KM;
                    message.setDistance(distance);
                }
            }
            return HttpResponse.OK(new PageInfo(list));
        }else {
            //查询商家
            List<Shop> shops = shopDao.shopData(adcode, classify, inputText, minLon, maxLon, minLat, maxLat);
            if (shops.size() !=0){
                for (Shop shop : shops) {
                    double shopLon =shop.getLon();
                    double shopLat =shop.getLat();
                    SpatialContext geo = SpatialContext.GEO;
                    double distance = geo.calcDistance(geo.makePoint(lon, lat), geo.makePoint(shopLon, shopLat)) * DistanceUtils.DEG_TO_KM;
                    shop.setDistance(distance);
                }
            }
            if (inputText !=null || !inputText.isEmpty()){
                //记录关键词搜索历史和用户搜索记录
            }
            return HttpResponse.OK(new PageInfo(shops));
        }
    }

    public HttpResponse saveAdvice(int userId, String content) {
        Advice advice = new Advice();
        advice.setUserId(userId);
        advice.setContent(content);
        advice.setCreateTime(new Date());
        adviceDao.insert(advice);
        return HttpResponse.OK("意见保存成功");
    }

    public HttpResponse warnInfo(Date warnTime, int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.warnInfo(warnTime,userId);
        List<Message> list = handleMessage(messages, userId);
        return HttpResponse.OK(new PageInfo(list));
    }

    public HttpResponse myWarnTime(int userId, Date startTime, Date endTime) {
        List<Date> dates = userWarnDao.myWarn(userId, startTime, endTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> warnTimes = new ArrayList();
        for (Date date : dates) {
            String warnTime = format.format(date);
            warnTimes.add(warnTime);
        }
        return HttpResponse.OK(warnTimes);
    }

    public HttpResponse myWarn(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.myWarnMessage(userId);
        List<Message> myLikes = handleMessage(messages, userId);
        return HttpResponse.OK(new PageInfo(myLikes));
    }

    public HttpResponse saveWarn(int userId, int messageId, int type) {
        UserWarn userWarn = new UserWarn();
        userWarn.setMessageId(messageId);
        userWarn.setUserId(userId);
        if (type == 0) {//保存提醒
            Message message = new Message();
            message.setMessageId(messageId);
            Message one = messageDao.selectOne(message);
            User user = getUser(userId);
            userWarn.setUserId(userId);
            userWarn.setUserName(user.getUsername());
            userWarn.setMessageId(messageId);
            userWarn.setWarnTime(one.getStartTime());
            userWarn.setCreateTime(new Date());
            userWarnDao.insert(userWarn);
        } else {//取消提醒
            userWarnDao.delete(userWarn);
        }
        return HttpResponse.OK("操作成功");

    }

    public HttpResponse myScan(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.myScanMessage(userId);
        List<Message> myLikes = handleMessage(messages, userId);
        return HttpResponse.OK(new PageInfo(myLikes));
    }

    public HttpResponse myLike(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.myLikeMessage(userId);
        List<Message> myLikes = handleMessage(messages, userId);
        return HttpResponse.OK(new PageInfo(myLikes));
    }

    public HttpResponse likeUser(int messageId) {
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        List<UserLike> likes = userLikeDao.select(userLike);
        return HttpResponse.OK(likes);
    }

    public HttpResponse commentUser(int userId, int messageId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserComment> comments = userCommentDao.commentUser(messageId);
        //监控此接口入库 用户浏览记录
        saveUserScan(userId, messageId);
        return HttpResponse.OK(new PageInfo(comments));

    }

    public HttpResponse saveComment(int messageId, int userId, String content) {
        User user = getUser(userId);
        UserComment userComment = new UserComment(messageId, userId, user.getUsername(), content, new Date());
        userCommentDao.insert(userComment);
        return HttpResponse.OK("评论成功");
    }

    /**
     * @description 用户对消息的点赞 或者取消 type=0 点赞 type=1 取消
     * @author 张宝帅
     * @date 2019/9/19 21:32
     */
    public HttpResponse saveLike(int messageId, int userId, int type) {
        User user = getUser(userId);
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        userLike.setUserId(userId);
        userLike.setUserName(user.getUsername());
        if (type == 1) {
            userLikeDao.delete(userLike);
        } else {
            userLike.setUpdateTime(new Date());
            userLikeDao.insert(userLike);
        }
        return HttpResponse.OK("操作成功");
    }

    public HttpResponse newMessage(double lon,double lat,int adcode, int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageDao.newMessage(adcode);
        List<Message> list = handleMessage(messages, userId);
        //计算距离
        if (list.size() !=0){
            for (Message message : list) {
                double messageLon =message.getLon();
                double messageLat =message.getLat();
                SpatialContext geo = SpatialContext.GEO;
                double distance = geo.calcDistance(geo.makePoint(lon, lat), geo.makePoint(messageLon, messageLat)) * DistanceUtils.DEG_TO_KM;
                message.setDistance(distance);
            }
        }
        return HttpResponse.OK(new PageInfo(list));
    }

    public HttpResponse createMessage(MessageParam param, Integer userId) {
        Message message = new Message();
        message.setUserId(userId);
        User user = getUser(userId);
        message.setUserName(user.getUsername());
        message.setAvatar(user.getAvatar());
        message.setTitle(param.getTitle());
        message.setContent(param.getContent());
        if (param.getAdcode() != 0) {
            message.setAdcode(param.getAdcode());
            message.setProvince(param.getProvince());
            message.setCity(param.getCity());
            message.setDistrict(param.getDistrict());
            message.setAddress(param.getAddress());
            message.setClassify(param.getClassify());
            //获取经纬度
            String location = accountService.getLocation(param.getProvince() + param.getCity() + param.getDistrict() + param.getAddress());
            String[] lonLat = StringUtils.split(location, ",");
            Double lon = Double.valueOf(lonLat[0]);
            Double lat = Double.valueOf(lonLat[1]);
            message.setLon(lon);
            message.setLat(lat);
        } else {
            //从shop表中获取当前商家信息的adcode
            Shop shop = new Shop();
            shop.setUserId(userId);
            Shop one = shopDao.selectOne(shop);
            message.setAdcode(one.getAdcode());
            message.setProvince(one.getProvince());
            message.setCity(one.getCity());
            message.setDistrict(one.getDistrict());
            message.setAddress(one.getAddress());
            message.setClassify(one.getClassify());
            message.setLon(one.getLon());
            message.setLat(one.getLon());
        }
        message.setRebate(param.getRebate());
        message.setStartTime(param.getStartTime());
        message.setFilePath(param.getFilePath());
        message.setCreateTime(new Date());
        messageDao.insert(message);

        return HttpResponse.OK("新的折扣消息发布成功");
    }

    /**
     * @description 根据userId获取user对象
     * @author 张宝帅
     * @date 2019/9/20 19:52
     */
    private User getUser(int userId) {
        User user = new User();
        user.setUserId(userId);
        User one = userDao.selectOne(user);
        return one;
    }

    /**
     * @description 入库 用户浏览记录
     * @author 张宝帅
     * @date 2019/9/20 19:40
     */
    private void saveUserScan(int userId, int messageId) {
        UserScan userScan = new UserScan();
        userScan.setUserId(userId);
        userScan.setMessageId(messageId);
        UserScan one = userScanDao.selectOne(userScan);
        if (one == null) {
            //新消息入库
            User user = getUser(userId);
            userScan.setUserName(user.getUsername());
            userScan.setUpdateTime(new Date());
            userScanDao.insert(userScan);
        } else {
            userScanDao.flushUpdateTime(messageId, userId, new Date());
            //更新updateTime
        }
    }

    /**
     * @description 处理messages
     * @author 张宝帅
     * @date 2019/9/20 19:17
     */
    private List<Message> handleMessage(List<Message> messages, int userId) {
        for (Message message : messages) {
            //处理三种状态
            Map<String, Object> map = handleStatus(message.getMessageId(), userId);
            message.setShopStatus((Integer) map.get("shopStatus"));
            message.setLikeStatus((Integer) map.get("likeStatus"));
            message.setWarnStatus((Integer) map.get("warnStatus"));
            message.setShopName((String) map.get("shopName"));
            message.setShopPhoto((String) map.get("shopPhoto"));
            //处理filePath
            String filePath = message.getFilePath();
            if (filePath != null && !"".equals(filePath)) {
                String[] filePaths = filePath.split(",");
                message.setFilePaths(filePaths);
            } else {
                message.setFilePaths(null);
            }
            //处理点赞数量 评论数量 提醒数量
            List<Integer> threeCount = messageDao.threeCount(message.getMessageId());
            message.setLikeCount(threeCount.get(0));
            message.setCommentCount(threeCount.get(1));
            message.setWarnCount(threeCount.get(2));
        }
        return messages;
    }

    /**
     * @description 处理当前用户 是否为商家 是否已点赞 是否已提醒 状态
     * @author 张宝帅
     * @date 2019/9/20 18:27
     */
    private Map<String, Object> handleStatus(int messageId, int userId) {
        HashMap map = new HashMap();
        //处理商家状态
        Shop shop = new Shop();
        shop.setUserId(userId);
        Shop shopOne = shopDao.selectOne(shop);
        if (shopOne == null) {
            map.put("shopStatus", 0);//不是商家
            map.put("shopName",null);
            map.put("shopPhoto",null);
        } else {
            map.put("shopStatus", 1);//是商家
            map.put("shopName",shopOne.getShopName());
            map.put("shopPhoto",shopOne.getShopPhoto());
        }
        //处理点赞状态
        UserLike userLike = new UserLike();
        userLike.setMessageId(messageId);
        userLike.setUserId(userId);
        UserLike likeOne = userLikeDao.selectOne(userLike);
        if (likeOne == null) {
            map.put("likeStatus", 0);//未点赞
        } else {
            map.put("likeStatus", 1);//已点赞
        }
        //处理提醒我状态
        UserWarn userWarn = new UserWarn();
        userWarn.setMessageId(messageId);
        userWarn.setUserId(userId);
        UserWarn one = userWarnDao.selectOne(userWarn);
        if (one == null) {
            map.put("warnStatus", 0);//未提醒
        } else {
            map.put("warnStatus", 1);//已提醒
        }
        return map;
    }

    private void saveSearchRecord(int userId,String inputText){

    }
}
