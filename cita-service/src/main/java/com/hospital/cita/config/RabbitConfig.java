package com.hospital.cita.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "hospital.eventos";
    public static final String COLA_NOTIFICACION = "cola.notificacion";
    public static final String ROUTING_KEY_CITA_CONFIRMADA = "cita.confirmada";

    @Bean
    public Queue colaNotificacion() {
        return new Queue(COLA_NOTIFICACION, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingNotificacion(Queue colaNotificacion, TopicExchange exchange) {
        return BindingBuilder.bind(colaNotificacion).to(exchange)
                .with(ROUTING_KEY_CITA_CONFIRMADA);
    }
}