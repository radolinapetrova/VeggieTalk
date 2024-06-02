package com.veggietalk.account_service.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Producer {

    private final RabbitTemplate template;

    @Autowired
    public Producer(RabbitTemplate template) {
        this.template = template;
    }

    public void deleteAccount(String accountId){
        try {
            template.convertAndSend(RabbitMqConfig.ACCOUNT_EXCHANGE, RabbitMqConfig.ROUTING_KEY_ACCOUNT, accountId);
            System.out.println("Message sent: " + accountId);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

