package com.videoclub.rabbitstreams;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class KeyCloakConsumer {

    @Bean
    public Consumer<Event<Integer, EventClientNotificationMqMsg>> messageProcessor() {
        return message -> {
            try {
                // server
                System.out.println("Mensaje recibido: " + message.getData());
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

    public class EventClientNotificationMqMsg {
        @JsonProperty("@class")
        private String className;
        private long time;
        private String type;
        private String realmId;
        private String clientId;
        private String userId;
        private String sessionId;
        private String ipAddress;
        private Map<String, String> details;

        // Getters y setters

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Instant getTime() {
            return Instant.ofEpochMilli(time);
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRealmId() {
            return realmId;
        }

        public void setRealmId(String realmId) {
            this.realmId = realmId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public Map<String, String> getDetails() {
            return details;
        }

        public void setDetails(Map<String, String> details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "EventClientNotificationMqMsg{" +
                    "className='" + className + '\'' +
                    ", time=" + getTime() +
                    ", type='" + type + '\'' +
                    ", realmId='" + realmId + '\'' +
                    ", clientId='" + clientId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", ipAddress='" + ipAddress + '\'' +
                    ", details=" + details +
                    '}';
        }
    }

}