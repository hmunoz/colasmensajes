package com.videoclub.rabbitstreams.service;


import com.videoclub.rabbitstreams.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class KkProcessorConfig {

    @Bean
    public Consumer<String> keycloakProcessor() {
        return message -> {
            try {
                // server
                log.info("Process message keycloakProcessor {}...", message);
                // Lógica de procesamiento del mensaje
                throw new EventProcessingException("Error procesando el mensaje");
            } catch (EventProcessingException e) {
                // Loggear la excepción para un análisis más detallado
                log.error(e.getCause().getMessage());
                // Si deseas realizar alguna acción adicional, como enviar un mensaje a una cola de alertas
                throw e;
            }
        };

    }
}