package com.zhikou.code.controller;

import com.zhikou.code.commons.ApiBaseAction;
import com.zhikou.code.commons.Constants;
import com.zhikou.code.commons.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/file")
public class FileController extends ApiBaseAction {

    @PostMapping("/upload")
    public ResponseEntity fileUpload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile file = req.getFile("file");
        //格式化时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String nowTime = sdf.format(new Date().getTime());
        String rootPath = System.getProperty("user.dir")+"/"+getUserId();
        String rootPath2 = System.getProperty("user.dir")+"/"+getUserId()+"/"+nowTime;
        String fileName = file.getOriginalFilename();
        File dir = new File(rootPath);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file1 = new File(rootPath2);
        if (!file1.exists()){
            file1.mkdir();
        }
        File uploadFile = new File(rootPath2,"/"+fileName);
        file.transferTo(uploadFile);
        HashMap map = new HashMap();
        map.put("path",uploadFile.getPath());
        System.out.println(uploadFile.getPath());
        HttpResponse response = new HttpResponse(Constants.ErrorCode.REQUEST_ERROR, map);
        return ResponseEntity.ok(response);
    }
}
