package com.veggietalk.account_service.config;

import com.veggietalk.account_service.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Consumer {

    private final AccountService service;

    @Autowired
    public Consumer(AccountService accountService) {
        this.service = accountService;
    }
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = RabbitMqConfig.ACCOUNT_QUEUE)
    public void receivePostMessage(Map<String, String> message) {
        logger.info("Received message: {}", message);
        String username = message.get("username");
        try{
            service.deleteByUser(username);
        }
        catch (IllegalArgumentException e){
            System.out.println("No such user");
        }
        System.out.println("Comments successfully deleted");
    }
}
