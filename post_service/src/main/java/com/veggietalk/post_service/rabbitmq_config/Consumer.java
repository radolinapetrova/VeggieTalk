package com.veggietalk.post_service.rabbitmq_config;


import com.veggietalk.post_service.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class Consumer {


    private final PostService service;

    @Autowired
    public Consumer(PostService postService) {
        this.service = postService;
    }

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = RabbitMqConfig.POST_QUEUE)
    public void receiveMessage(String accountId) {
        logger.info("Received message: {}", accountId);
        // Add your processing logic here
        System.out.println("Posts successfully deleted");
        service.deleteByAccountId(UUID.fromString(accountId));
    }
}
