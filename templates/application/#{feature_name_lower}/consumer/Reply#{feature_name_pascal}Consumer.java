// template path: application/#{feature_name_lower}/consumer/Reply#{feature_name_pascal}Consumer.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.consumer;

import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

public interface Reply#{feature_name_pascal}Consumer {
    void execute(String payload, ConsumerRecordMetadata metadata);
}