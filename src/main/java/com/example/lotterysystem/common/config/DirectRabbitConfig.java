package com.example.lotterysystem.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {
    public static final String EXCHANGE_NAME = "DirectExchange";
    public static final String QUEUE_NAME = "DirectQueue";
    public static final String ROUTING = "DirectRouting";

    @Bean
    public Queue directQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(ROUTING);
    }
}