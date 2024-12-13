// template path: adapter/#{feature_name_lower}/producer/#{feature_name_pascal}EventMessagingAdapter.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.producer;

import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.MessageInfo;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventMessagingGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier("#{feature_name_lower}")
public class #{feature_name_pascal}EventMessagingAdapter implements EventMessagingGateway {

    @Value("${spring.kafka.topico-#{feature_name_lower}-resp}")
    private final String replyTopic;

    @Value("${spring.kafka.topico-#{feature_name_lower}-req}")
    private final String requestTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Override
    public MessageInfo send(String adminId, Event event) {
        String timestamp = Instant.now().toString().replace("Z", "");

        ObjectNode header = mapper.createObjectNode();
        header.put("entidade", "#{feature_name_kebab}");
        header.put("evento", "INCLUSAO");
        header.putPOJO("numOp", event.getOperationId());
        header.put("servicoOrig", event.getSource());
        header.put("participante", adminId);
        header.put("versao", 1);
        header.put("timestamp", timestamp);
        header.put("topicoReposta", replyTopic);

        ObjectNode body;
        try {
            body = mapper.readValue(event.getRequestPayload(), ObjectNode.class);
        } catch (JsonProcessingException e) {
            log.error("error when converting payload to object node to create body", e);
            return MessageInfo.empty();
        }

        ObjectNode payload = mapper.createObjectNode();
        payload.set("header", header);
        payload.set("body", body);

        try {
            RecordMetadata recordMetadata = kafkaTemplate.send(requestTopic, payload.toString())
                    .get()
                    .getRecordMetadata();

            return MessageInfo.create(recordMetadata.offset(), recordMetadata.partition());
        } catch (Exception e) {
            log.error("failed to send event with id {}", event.getId(), e);
            return MessageInfo.empty();
        }
    }
}