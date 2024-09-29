package com.videoclub.colasmensajes;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class Receiver {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message, Channel channel, Message amqpMessage) {
        try {
            // Procesa el mensaje aquí
            System.out.println("Message received: " + message);
            // Confirma manualmente que el mensaje ha sido recibido
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // Si algo falla, puedes reenviar el mensaje a la cola o descartarlo
            try {
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}