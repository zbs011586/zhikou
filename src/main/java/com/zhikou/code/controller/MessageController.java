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

    @PostMapping("/show")
    public ResponseEntity showMessage(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.showMessage(getUserId(),param.getPageNum(),param.getPageSize()));
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
        return ResponseEntity.ok(messageService.commentUser(param.getMessageId(),param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/like/user")
    public ResponseEntity likeUser(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.likeUser(param.getMessageId()));
    }
}
