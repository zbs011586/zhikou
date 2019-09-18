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

}
