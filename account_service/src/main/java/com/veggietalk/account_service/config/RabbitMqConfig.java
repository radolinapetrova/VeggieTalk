package com.veggietalk.account_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String POST_EXCHANGE = "post-exchange";
    public static final String POST_QUEUE = "post-queue";
    public static final String ROUTING_KEY_POST = "post";

    public static final String COMMENT_EXCHANGE = "comment-exchange";
    public static final String COMMENT_QUEUE = "comment-queue";
    public static final String ROUTING_KEY_COMMENT = "comment";

    @Bean
    public DirectExchange postExchange() {
        return new DirectExchange(POST_EXCHANGE);
    }

    @Bean DirectExchange accountExchange(){return new DirectExchange(COMMENT_EXCHANGE);}

    @Bean
    public Queue postQueue() {
        return new Queue(POST_QUEUE);
    }

    @Bean
    public Queue accountQueue() {return new Queue(COMMENT_QUEUE);}

    @Bean
    public Binding postBinding() {
        return BindingBuilder.bind(postQueue()).to(postExchange()).with(ROUTING_KEY_POST);
    }

    @Bean
    public Binding accountBinding(){
        return BindingBuilder.bind(accountQueue()).to(accountExchange()).with(ROUTING_KEY_COMMENT);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("java.util", "com.your.package.name");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
