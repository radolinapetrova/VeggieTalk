package com.veggietalk.comment_service.rabbitmq_config;


import com.veggietalk.comment_service.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class Consumer {


    private final CommentService service;

    @Autowired
    public Consumer(CommentService commentService) {
        this.service = commentService;
    }

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

//    @RabbitListener(queues = RabbitMqConfig.POST_COMMENT_QUEUE)
//    public void receivePostMessage(String postId) {
//        logger.info("Received message: {}", postId);
//        // Add your processing logic here
//        service.deleteByPostId(UUID.fromString(postId));
//        System.out.println("Comments successfully deleted");
//    }

    @RabbitListener(queues = RabbitMqConfig.COMMENT_QUEUE)
    public void receiveAccMessage(String accountId){
        logger.info("Received message: {}", accountId);
        service.deleteByAccountId(UUID.fromString(accountId));
        System.out.println("Comments successfully deleted");
    }

}
