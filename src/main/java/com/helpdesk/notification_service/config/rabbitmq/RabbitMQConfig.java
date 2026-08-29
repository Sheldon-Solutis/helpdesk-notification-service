package com.helpdesk.notification_service.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean("helpdesk.exchange")
    public TopicExchange topicExchange() {}

    @Bean
    public Queue notificationQueue() {}


}
