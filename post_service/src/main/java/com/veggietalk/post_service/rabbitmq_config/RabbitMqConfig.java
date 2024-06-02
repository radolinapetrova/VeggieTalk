package com.veggietalk.post_service.rabbitmq_config;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
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

    public static final String ACCOUNT_QUEUE = "account-queue";

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
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