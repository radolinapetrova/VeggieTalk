//package com.veggietalk.post_service.rabbitmq_config;
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMqConfig {
//    public static final String ACCOUNT_EXCHANGE = "account-exchange";
//    public static final String ACCOUNT_QUEUE = "account-queue";
//
//    public static final String ROUTING_KEY_ACCOUNT = "account";
//
//    @Bean
//    public DirectExchange postEventsExchange() {
//        return new DirectExchange(ACCOUNT_EXCHANGE);
//    }
//
//    @Bean
//    public Queue accountServiceQueue() {
//        return new Queue(ACCOUNT_QUEUE);
//    }
//
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(accountServiceQueue()).to(postEventsExchange()).with(ROUTING_KEY_ACCOUNT);
//    }
//}