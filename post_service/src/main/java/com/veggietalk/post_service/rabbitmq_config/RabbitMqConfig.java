package com.veggietalk.post_service.rabbitmq_config;
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

    public static final String POST_COMMENT_EXCHANGE = "post-comment-exchange";
    public static final String POST_COMMENT_QUEUE = "post-comment-queue";
    public static final String ROUTING_KEY_POST_COMMENT = "post-comment";

    public static final String POST_QUEUE = "post-queue";

    @Bean
    public DirectExchange postCommentExchange() {
        return new DirectExchange(POST_COMMENT_EXCHANGE);
    }

    @Bean
    public Queue postCommentQueue() {
        return new Queue(POST_COMMENT_QUEUE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(postCommentQueue()).to(postCommentExchange()).with(ROUTING_KEY_POST_COMMENT);
    }



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