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
        return ResponseEntity.ok(messageService.showMessage(param.getPageNum(),param.getPageSize()));
    }

    @PostMapping("/user/like")
    public ResponseEntity userLike(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.userLike(param.getMessageId(),getUserId(),param.getType()));
    }

    @PostMapping("/is/like")
    public ResponseEntity isLike(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.isLike(param.getMessageId(),getUserId()));
    }

    @PostMapping("/user/comment")
    public ResponseEntity userComment(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.userComment(param.getMessageId(),getUserId(),param.getContent()));
    }

    @PostMapping("/message/comment")
    public ResponseEntity messageComment(@RequestBody MessageParam param){
        return ResponseEntity.ok(messageService.messageComment(param.getMessageId(),param.getPageNum(),param.getPageSize()));
    }
}
