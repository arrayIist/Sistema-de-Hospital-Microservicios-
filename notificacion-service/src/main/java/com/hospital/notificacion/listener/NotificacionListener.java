package com.hospital.notificacion.listener;

import com.hospital.notificacion.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificacionListener {

    @RabbitListener(queues = RabbitConfig.COLA_NOTIFICACION)
    public void manejarCitaConfirmada(Map<String, Object> evento) {
        System.out.println("========================================");
        System.out.println("NOTIFICACIÓN RECIBIDA");
        System.out.println("Cita ID: " + evento.get("citaId"));
        System.out.println("Paciente ID: " + evento.get("pacienteId"));
        System.out.println("Estado: " + evento.get("estado"));
        System.out.println("Fecha: " + evento.get("fecha"));
        System.out.println("========================================");
    }
}