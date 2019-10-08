package com.zhikou.code.controller;

import com.zhikou.code.commons.ApiBaseAction;
import com.zhikou.code.commons.IgnoreAuth;
import com.zhikou.code.param.FansParam;
import com.zhikou.code.param.UserParam;
import com.zhikou.code.service.FansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fans")
public class FansController extends ApiBaseAction {

    @Autowired
    private FansService fansService;

    @PostMapping("/status")
    public ResponseEntity isFans(@RequestBody FansParam param){
        return ResponseEntity.ok(fansService.isFans(param.getConcernUserId(),getUserId()));
    }

    @PostMapping("/count")
    public ResponseEntity getConcernAndFansCount(){
        return ResponseEntity.ok(fansService.getConcernAndFansCount(getUserId()));
    }

    @IgnoreAuth
    @PostMapping("/concern/list")
    public ResponseEntity getConcernList(@RequestBody UserParam param){
        return ResponseEntity.ok(fansService.getConcernList(param.getUserId()));
    }

    @IgnoreAuth
    @PostMapping("/fans/list")
    public ResponseEntity getFansList(@RequestBody UserParam param){
        return ResponseEntity.ok(fansService.getFansList(param.getUserId()));
    }

    @PostMapping("/save")
    public ResponseEntity saveFansConcern(@RequestBody FansParam param){
        return ResponseEntity.ok(fansService.saveFansConcern(param.getConcernUserId(),getUserId(),param.getType()));
    }

}
