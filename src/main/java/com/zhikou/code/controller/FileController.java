package com.zhikou.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhikou.code.commons.ApiBaseAction;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.commons.HttpResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController extends ApiBaseAction {

    @PostMapping("/upload")
    public ResponseEntity fileUpload(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = req.getFiles("file");
        /*先进行图片鉴黄*/
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            boolean b = imgCheck(file);
            if (!b){
                HttpResponse response = new HttpResponse(Constants.ErrorCode.IMG_ERROR, "第" +(i+1)+ "张图片不合法");
                return ResponseEntity.ok(response);
            }
        }
        String urls = "";
        if (files !=null && files.size()>0){
            for (MultipartFile file : files) {
                //文件后缀
                String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                //rootPath为linux环境下的绝对路径+根据userId生产的文件夹
                String rootPath = "/root/zhiko/zhikou/image"+"/"+getUserId();
                //用时间戳重新命名文件
                String newFileName = new Date().getTime()+"."+fileSuffix;
                String filePath = rootPath+"/"+newFileName;
                File fileDir = new File(rootPath);
                File uploadFile = new File(filePath);
                try {
                    if (!fileDir.exists()){
                        fileDir.mkdirs();
                    }
                    if (!uploadFile.exists()){
                        file.transferTo(uploadFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //生成图片的静态资源访问路径
                //String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/image/"+getUserId()+"/"+newFileName;
                String url = "https://www.zhiko.store/api/image/"+getUserId()+"/"+newFileName;
                urls += url +",";
            }

            HttpResponse response = new HttpResponse(Constants.ErrorCode.OK,urls.substring(0,urls.length() - 1));
            return ResponseEntity.ok(response);
        }else {
            HttpResponse response = new HttpResponse(Constants.ErrorCode.REQUEST_ERROR,"文件为空,请重新上传");
            return ResponseEntity.ok(response);
        }
    }

    /*获取微信小程序的AccessToken*/
    private String getAccessToken(){
        Map map = new HashMap();
        map.put("grant_type","client_credential");
        map.put("appid","wxce9b581e958ee216");
        map.put("secret","ba70e6c8428b2489b7b722fb6b8f845b");
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> entity = template.getForEntity("https://api.weixin.qq.com/cgi-bin/token?grant_type={grant_type}&appid={appid}&secret={secret}", String.class, map);
        JSONObject object = JSONObject.parseObject(entity.getBody());
        return object.getString("access_token");
    }

    /*小程序图片鉴黄*/
    private boolean imgCheck(MultipartFile file) throws Exception {
        String accessToken = getAccessToken();
        RestTemplate template = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        /*设置请求头*/
        httpHeaders.add("Accept",MediaType.APPLICATION_JSON.toString());
        httpHeaders.setContentType(MediaType.parseMediaType("multipart/form-data;charset=UTF-8"));

        /*一个键对应多个值 表单提交*/
        MultiValueMap<String,Object> map = new LinkedMultiValueMap();
        /*RestTemplate中的file属性必须是FileSystemResource  不能是MultipartFile
        * MultipartFile 直接转 fileSystemResource 是不行的
        * FileSystemResource 需要根据文件路径 来构造
        * multipartFile 没有路径属性
        * 需要用java临时文件路径
        * System.getProperty("java.io.tmpdir") 返回的是临时目录的路径
        * 临时文件在win和linux中都会自动删除*/
        File tempFilePath = multipartFileToFile(file);
        FileSystemResource resource = new FileSystemResource(tempFilePath);//把临时文件变成filesystemresource
        map.add("media",resource);
        HttpEntity entity = new HttpEntity(map, httpHeaders);
        ResponseEntity<String> responseEntity = template.postForEntity("https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken, entity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        if (jsonObject.getIntValue("errcode") == 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}

