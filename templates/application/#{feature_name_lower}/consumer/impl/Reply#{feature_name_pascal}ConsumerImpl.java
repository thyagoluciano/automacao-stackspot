// template path: application/#{feature_name_lower}/consumer/impl/Reply#{feature_name_pascal}ConsumerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.consumer.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.consumer.Reply#{feature_name_pascal}Consumer;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Reply;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "feat.#{feature_name_kebab}.resposta.ativo", havingValue = "true")
@RequiredArgsConstructor
public class Reply#{feature_name_pascal}ConsumerImpl implements Reply#{feature_name_pascal}Consumer {

    @Qualifier("#{feature_name_lower}")
    private final ReplyService service;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = {
                    "${spring.kafka.topico-#{feature_name_lower}-resp}",
            }
    )
    @Override
    public void execute( @Payload String payload, ConsumerRecordMetadata metadata ) {
        Reply reply = Reply.create(metadata.offset(), metadata.partition(), payload);

        service.execute(reply);
    }
}