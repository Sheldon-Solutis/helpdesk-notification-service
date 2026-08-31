package com.helpdesk.notification_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "helpdesk.exchange";
    public static final String TICKET_QUEUE = "ticket.queue";
    public static final String TICKET_ROUTING_KEY = "ticket.*";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue ticketQueue() {
        return QueueBuilder
                .durable(TICKET_QUEUE)
                .build();
    }

    @Bean
    public Binding ticketBinding(
            Queue ticketQueue,
            TopicExchange helpdeskExchange
    ) {
        return BindingBuilder
                .bind(ticketQueue)
                .to(helpdeskExchange)
                .with(TICKET_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
