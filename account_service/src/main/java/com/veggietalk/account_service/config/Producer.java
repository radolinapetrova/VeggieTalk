//package com.veggietalk.account_service.config;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Producer {
//
//    @Autowired
//    private RabbitTemplate template;
//
//    public void verifyAccount(Long accountId){
//        template.convertAndSend(RabbitMqConfig.ACCOUNT_EXCHANGE, RabbitMqConfig.ROUTING_KEY_ACCOUNT, accountId);
//    }
//
//}
