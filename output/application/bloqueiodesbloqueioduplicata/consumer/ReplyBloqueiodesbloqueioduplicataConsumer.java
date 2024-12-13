// template path: application/bloqueiodesbloqueioduplicata/consumer/ReplyBloqueiodesbloqueioduplicataConsumer.java
package br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.consumer;

import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

public interface ReplyBloqueiodesbloqueioduplicataConsumer {
    void execute(String payload, ConsumerRecordMetadata metadata);
}