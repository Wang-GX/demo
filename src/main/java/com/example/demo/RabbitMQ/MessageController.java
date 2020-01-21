package com.example.demo.RabbitMQ;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "RabbitMQ", tags = "RabbitMQ")
@RestController
@Slf4j
@RequestMapping("/mq")
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @ApiOperation(value = "消息发送：receive")
    @PostMapping("/receive")
    public void send1() {
        messageSender.receive();
    }

    @ApiOperation(value = "消息发送：receiveBack")
    @PostMapping("/receiveBack")
    public void send2() {
        messageSender.receiveBack();
    }

    @ApiOperation(value = "消息发送：delayQueue")
    @PostMapping("/delayQueue")
    public void send3() {
        messageSender.delay();
    }

    @ApiOperation(value = "消息发送：delayMsg")
    @PostMapping("/delayMsg")
    public void send4() {
        messageSender.delayMsg();
    }

}


