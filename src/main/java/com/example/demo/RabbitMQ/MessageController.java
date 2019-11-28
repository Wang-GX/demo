package com.example.demo.RabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/mq")
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @PostMapping("/receive")
    public void send() {
        messageSender.receive();
    }

    @PostMapping("/receiveBack")
    public void send2() {
        messageSender.receiveBack();
    }

}


