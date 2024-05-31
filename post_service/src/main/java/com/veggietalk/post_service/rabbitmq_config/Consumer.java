package com.veggietalk.post_service.rabbitmq_config;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Consumer {
    @RabbitListener(queues = RabbitMqConfig.ACCOUNT_QUEUE)
    public void deletedAccount(UUID accountId){
        System.out.println("Account verified: " + accountId);
    }
}
