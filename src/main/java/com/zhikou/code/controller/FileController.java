package com.zhikou.code.controller;

import com.zhikou.code.commons.ApiBaseAction;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.commons.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController extends ApiBaseAction {

    @PostMapping("/upload")
    public ResponseEntity fileUpload(HttpServletRequest request){
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = req.getFiles("file");
        log.info(files.size()+"");
        String urls = "";
        if (files !=null && files.size()>0){
            for (MultipartFile file : files) {
                //文件后缀
                String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                //rootPath为linux环境下的绝对路径+根据userId生产的文件夹
                String rootPath = "/root/zhiko/zhikou/image/"+getUserId();
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
                String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/image/"+getUserId()+"/"+newFileName;
                log.info(url);
                urls = url +",";
            }
            log.info(urls);
            HttpResponse response = new HttpResponse(Constants.ErrorCode.OK,urls);
            return ResponseEntity.ok(response);
        }else {
            HttpResponse response = new HttpResponse(Constants.ErrorCode.REQUEST_ERROR,"文件为空,请重新上传");
            return ResponseEntity.ok(response);
        }
    }
}
