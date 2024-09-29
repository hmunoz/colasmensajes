package com.videoclub.rabbitstreams;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
public class KeyCloakConsumer {

    @Bean
    public Consumer<String> keycloakProcessor() {
        return message -> {
            try {
                // server
                System.out.println("Mensaje recibido: " + message);
                // Lógica de procesamiento del mensaje
                throw new RuntimeException("Error procesando el mensaje");
            } catch (Exception e) {
                // Loggear la excepción para un análisis más detallado
                System.out.println(e.getCause().getMessage());
                // Si deseas realizar alguna acción adicional, como enviar un mensaje a una cola de alertas
                throw e;
            }
        };

    }
}