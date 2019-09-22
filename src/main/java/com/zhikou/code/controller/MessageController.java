package com.zhikou.code.controller;

import com.zhikou.code.commons.ApiBaseAction;
import com.zhikou.code.param.MessageParam;
import com.zhikou.code.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController extends ApiBaseAction {

    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity createMessage(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.createMessage(param,getUserId()));
    }

    @PostMapping("/new/message")
    public ResponseEntity showMessage(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.newMessage(getUserId(),param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/save/like")
    public ResponseEntity userLike(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.saveLike(param.getMessageId(),getUserId(),param.getType()));
    }

    @PostMapping("/save/comment")
    public ResponseEntity userComment(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.saveComment(param.getMessageId(),getUserId(),param.getContent()));
    }

    @PostMapping("/comment/user")
    public ResponseEntity commentUser(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.commentUser(getUserId(),param.getMessageId(),param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/like/user")
    public ResponseEntity likeUser(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.likeUser(param.getMessageId()));
    }

    @PostMapping("/my/like")
    public ResponseEntity myLikes(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.myLike(getUserId(),param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/my/scan")
    public ResponseEntity myScan(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.myScan(getUserId(),param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/save/warn")
    public ResponseEntity saveWarn(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.saveWarn(getUserId(),param.getMessageId(),param.getType()));
    }

    @PostMapping("/my/warn")
    public ResponseEntity myWarn(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.myWarn(getUserId(),param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/warn/time")
    public ResponseEntity myWarnTime(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.myWarnTime(getUserId(),param.getStartTime(),param.getEndTime()));
    }

    @PostMapping("/warn/info")
    public ResponseEntity warnInfo(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.warnInfo(param.getWarnTime(),getUserId()));
    }

}
