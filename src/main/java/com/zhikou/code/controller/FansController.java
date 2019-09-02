package com.zhikou.code.controller;

import com.zhikou.code.bean.Fans;
import com.zhikou.code.commons.ApiBaseAction;
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
    public ResponseEntity isFans(@RequestBody Fans fans){
        Integer userId = this.getUserId();
        return ResponseEntity.ok(fansService.isFans(fans.getConcernUserId(),userId));
    }

    @PostMapping("/count")
    public ResponseEntity getConcernAndFansCount(){
        Integer userId = this.getUserId();
        return ResponseEntity.ok(fansService.getConcernAndFansCount(userId));
    }

    @PostMapping("/concern/list")
    public ResponseEntity getConcernList(){
        Integer userId = this.getUserId();
        return ResponseEntity.ok(fansService.getConcernList(userId));
    }

    @PostMapping("/fans/list")
    public ResponseEntity getFansList(){
        Integer userId = this.getUserId();
        return ResponseEntity.ok(fansService.getFansList(userId));
    }

    @PostMapping("/save")
    public ResponseEntity saveConcern(@RequestBody Fans fans){
        Integer userId = this.getUserId();
        return ResponseEntity.ok(fansService.saveConcern(fans.getConcernUserId(),userId));
    }

    @PostMapping("/del")
    public ResponseEntity delConcern(@RequestBody Fans fans){
        Integer userId = this.getUserId();
        return ResponseEntity.ok(fansService.delConcern(fans.getConcernUserId(),userId));
    }
}
