package com.veggietalk.post_service.rabbitmq_config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private final RabbitTemplate template;

    @Autowired
    public Producer(RabbitTemplate template) {
        this.template = template;
    }

    public void deletePost(String accountId){
        try {
            template.convertAndSend(RabbitMqConfig.POST_COMMENT_EXCHANGE, RabbitMqConfig.ROUTING_KEY_POST_COMMENT, accountId);
            System.out.println("Message sent: " + accountId);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
