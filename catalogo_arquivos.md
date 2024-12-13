## adapter/extract/database/ExtractOperationDatabaseAdapter.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/database/ExtractOperationDatabaseAdapter.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database;

import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity.ExtractOperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.repository.ExtractOperationRepository;
import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.mapper.ExtractOperationEntityMapper;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.FAILED_OPERATION_ID;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.PENDING_OPERATION_ID;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.SUCCESS_OPERATION_ID;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.failedOperation;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.pendingOperation;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.successOperation;

@Component
@RequiredArgsConstructor
@Qualifier("extract")
public class ExtractOperationDatabaseAdapter implements OperationDatabaseGateway {

    private final ExtractOperationRepository repository;
    private final ExtractOperationEntityMapper mapper;

    @Override
    public void save(Operation operation) {
        ExtractOperationEntity entity = mapper.toEntity(operation);

        repository.save(entity);

        operation.setId(entity.getId());
    }

    @Override
    public Operation findById(UUID id) {
        if (SUCCESS_OPERATION_ID.equals(id)) {
            return successOperation();
        }

        if (FAILED_OPERATION_ID.equals(id)) {
            return failedOperation();
        }

        if (PENDING_OPERATION_ID.equals(id)) {
            return pendingOperation();
        }

        ExtractOperationEntity entity = repository.findById(id).orElse(null);

        return mapper.toModel(entity);
    }

}

```

## adapter/extract/database/ExtractEventDatabaseAdapter.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/database/ExtractEventDatabaseAdapter.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database;

import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity.ExtractEventEntity;
import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.repository.ExtractEventRepository;
import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.mapper.ExtractEventEntityMapper;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.model.Event;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.FAILED_OPERATION_ID;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.PENDING_OPERATION_ID;
import static br.com.nuclea.canal.participante.dupcanalparticipanteapi.utils.Constants.SUCCESS_OPERATION_ID;

@Component
@RequiredArgsConstructor
@Qualifier("extract")
public class ExtractEventDatabaseAdapter implements EventDatabaseGateway {

    private final ExtractEventRepository repository;
    private final ExtractEventEntityMapper mapper;

    @Override
    public void save(Event event) {
        ExtractEventEntity entity = mapper.toEntity(event);

        repository.save(entity);

        event.setId(entity.getId());
    }

    @Override
    public Event findByOperationId(UUID operationId) {
        if (SUCCESS_OPERATION_ID.equals(operationId)) {
            return successEvent();
        }

        if (FAILED_OPERATION_ID.equals(operationId)) {
            return failedEvent();
        }

        if (PENDING_OPERATION_ID.equals(operationId)) {
            return pendingEvent();
        }

        ExtractEventEntity entity = repository.findByOperationId(operationId);

        return mapper.toModel(entity);
    }

    public static Event successEvent() {
        String replyPayload = """
                  {
                    "codigoIdentificacaoDuplicata": "OdOJynmGdHyR47psZoOC",
                    "numeroFatura": "KkTKvY3kITelgW0qUSKOcFbs7ZIBZNS0yghTXUdEiS3VdfPVPq23Nv"
                  }
                """;
        Event event = new Event(SUCCESS_OPERATION_ID, "");
        event.setReplyPayload(replyPayload);
        event.setId(SUCCESS_OPERATION_ID);
        return event;
    }

    public static Event failedEvent() {
        Event event = new Event(FAILED_OPERATION_ID, "");
        event.setId(FAILED_OPERATION_ID);
        return event;
    }

    public Event pendingEvent() {
        Event event = new Event(PENDING_OPERATION_ID, "");
        event.setId(PENDING_OPERATION_ID);
        return event;
    }
}

```

## adapter/extract/database/repository/ExtractEventRepository.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/database/repository/ExtractEventRepository.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity.ExtractEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractEventRepository extends JpaRepository<ExtractEventEntity, UUID> {
    ExtractEventEntity findByOperationId(UUID operationId);
}

```

## adapter/extract/database/repository/ExtractOperationRepository.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/database/repository/ExtractOperationRepository.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity.ExtractOperationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractOperationRepository extends JpaRepository<ExtractOperationEntity, UUID> {
}

```

## adapter/extract/database/entity/ExtractOperationEntity.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/database/entity/ExtractOperationEntity.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity;

import br.com.nuclea.dupcanalparticipanteapi.adapter.generic.database.OperationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dup_014_op")
public class ExtractOperationEntity extends OperationEntity {

}

```

## adapter/extract/database/entity/ExtractEventEntity.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/database/entity/ExtractEventEntity.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity;

import br.com.nuclea.dupcanalparticipanteapi.adapter.generic.database.EventEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "dup_014_evt")
@Entity
public class ExtractEventEntity extends EventEntity {
}

```

## adapter/extract/producer/ExtractEventMessagingAdapter.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/producer/ExtractEventMessagingAdapter.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.producer;

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
@Qualifier("extract")
public class ExtractEventMessagingAdapter implements EventMessagingGateway {

    @Value("${spring.kafka.topico-dup014-resp}")
    private final String replyTopic;

    @Value("${spring.kafka.topico-dup014-req}")
    private final String requestTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Override
    public MessageInfo send(String adminId, Event event) {
        String timestamp = Instant.now().toString().replace("Z", "");

        ObjectNode header = mapper.createObjectNode();
        header.put("entidade", "solicitacao-extrato-duplicata");
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

```

## adapter/extract/mapper/ExtractOperationEntityMapper.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/mapper/ExtractOperationEntityMapper.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity.ExtractOperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ExtractOperationEntityMapper {
    ExtractOperationEntity toEntity(Operation operation);

    Operation toModel(ExtractOperationEntity entity);
}

```

## adapter/extract/mapper/ExtractEventEntityMapper.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/adapter/extract/mapper/ExtractEventEntityMapper.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.adapter.extract.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.extract.database.entity.ExtractEventEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.model.Event;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ExtractEventEntityMapper {
    ExtractEventEntity toEntity(Event model);

    Event toModel(ExtractEventEntity entity);
}

```

## application/extract/controller/CreateExtractController.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/controller/CreateExtractController.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.controller;

import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupCommandResponse;
import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupQueryResponseError;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.extract.request.CreateExtractRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface CreateExtractController {
    @Operation(
            tags = {"Solicitação do Extrato da Duplicata"},
            operationId = "create-extract-request",
            summary = "DUP014, Solicitação do Extrato da Duplicata",
            description = "Assincrono, um id sera gerado para realizar polling no endpoint de retorno",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito - Requisição em processamento.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupCommandResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Erro ao processar requisição.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Dados do cabeçalho incorretos.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "412", description = "Precondiction Failed - Dados da requisição inconsistentes.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))})
            }
    )
    @PostMapping("/escrituradora/duplicata/extrato/incluir")
    ResponseEntity<CreateResponse> execute(
            @RequestHeader("X-CIP-Party-Admin-Id") @Pattern(regexp = "^[A-Za-z0-9-]+$") String adminId,
            @RequestBody @Valid CreateExtractRequest request
    );
}

```

## application/extract/controller/QueryExtractController.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/controller/QueryExtractController.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.controller;

import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupQueryResponseError;
import br.com.nuclea.dupcanalparticipanteapi.application.exchangeAct.request.QueryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface QueryExtractController {

    @Operation(
            tags = {"query-extract"},
            operationId = "query-extract-request",
            summary = "DUP0014RET, Solicitar extrato de duplicata",
            description = "Assincrono, DUP014RET",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Consulta sem resultado.", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Erro ao processar requisição.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Dados do cabeçalho incorretos.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "404", description = "Not Found.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "412", description = "Precondiction Failed - Dados da requisição inconsistentes.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) })
            }
    )
    @PostMapping(value = "/escrituradora/duplicata/extrato/retorno", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> execute(
            @RequestHeader("X-CIP-Party-Admin-Id") @Pattern(regexp = "^[A-Za-z0-9-]+$") String adminId,
            @RequestBody @Valid QueryRequest request
    ) throws Exception;
}

```

## application/extract/controller/impl/QueryExtractControllerImpl.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/controller/impl/QueryExtractControllerImpl.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.exchangeAct.request.QueryRequest;
import br.com.nuclea.dupcanalparticipanteapi.application.extract.controller.QueryExtractController;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "feat.solicitacao-extrato-duplicata.consulta.ativo", havingValue = "true")
public class QueryExtractControllerImpl implements QueryExtractController {

    private final QueryService service;

    public QueryExtractControllerImpl(@Qualifier("extract") QueryService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> execute(String adminId, QueryRequest request) throws Exception {
        String payload = service.execute(adminId, request.getOperationId());

        return ResponseEntity.ok(payload);
    }
}

```

## application/extract/controller/impl/CreateExtractControllerImpl.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/controller/impl/CreateExtractControllerImpl.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.common.mapper.CreateResponseMapper;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.extract.request.CreateExtractRequest;
import br.com.nuclea.dupcanalparticipanteapi.application.extract.controller.CreateExtractController;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ConditionalOnProperty(name = "feat.solicitacao-extrato-duplicata.criacao.ativo", havingValue = "true")
@RequiredArgsConstructor
public class CreateExtractControllerImpl implements CreateExtractController {

    @Qualifier("extract")
    private final CreateService service;
    private final CreateResponseMapper mapper;

    @Override
    public ResponseEntity<CreateResponse> execute(String adminId, CreateExtractRequest request) {
        Operation operation = this.service.execute(adminId, request.toJson());

        CreateResponse response = mapper.toResponse(operation);

        return ResponseEntity.accepted().body(response);
    }
}

```

## application/extract/request/CreateExtractRequest.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/request/CreateExtractRequest.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateExtractRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]{20}$")
    @Schema(description = "Codigo identificador da duplicata ")
    private String codigoIdentificacaoDuplicata;

    @Pattern(regexp = "^[a-zA-Z0-9]{1,60}$")
    @Size(min = 1, max = 60)
    @Schema(description = "Numero da fatura")
    private String numeroFatura;


    public String toJson() {
        try {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}

```

## application/extract/consumer/ReplyExtractConsumer.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/consumer/ReplyExtractConsumer.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.consumer;

import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

public interface ReplyExtractConsumer {
    void execute(String payload, ConsumerRecordMetadata metadata);
}

```

## application/extract/consumer/impl/ReplyExtractConsumerImpl.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/application/extract/consumer/impl/ReplyExtractConsumerImpl.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.application.extract.consumer.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.extract.consumer.ReplyExtractConsumer;
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
@ConditionalOnProperty(name = "feat.solicitacao-extrato-duplicata.resposta.ativo", havingValue = "true")
@RequiredArgsConstructor
public class ReplyExtractConsumerImpl implements ReplyExtractConsumer {

    @Qualifier("extract")
    private final ReplyService service;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = {
                    "${spring.kafka.topico-dup014-resp}",
            }
    )
    @Override
    public void execute( @Payload String payload, ConsumerRecordMetadata metadata ) {
        Reply reply = Reply.create(metadata.offset(), metadata.partition(), payload);

        service.execute(reply);
    }
}

```

## domain/extract/service/impl/QueryExtractServiceImpl.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/domain/extract/service/impl/QueryExtractServiceImpl.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.domain.extract.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("extract")
@RequiredArgsConstructor
public class QueryExtractServiceImpl extends QueryService {

    @Qualifier("extract")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("extract")
    private final EventDatabaseGateway eventDatabaseGateway;

    @Override
    public OperationDatabaseGateway setOperationGateway() {
        return operationDatabaseGateway;
    }

    @Override
    public EventDatabaseGateway setEventGateway() {
        return eventDatabaseGateway;
    }
}

```

## domain/extract/service/impl/ReplyExtractServiceImpl.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/domain/extract/service/impl/ReplyExtractServiceImpl.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.domain.extract.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("extract")
@RequiredArgsConstructor
public class ReplyExtractServiceImpl extends ReplyService {

    @Qualifier("extract")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("extract")
    private final EventDatabaseGateway eventDatabaseGateway;

    @Override
    public OperationDatabaseGateway setOperationGateway() {
        return operationDatabaseGateway;
    }

    @Override
    public EventDatabaseGateway setEventGateway() {
        return eventDatabaseGateway;
    }
}

```

## domain/extract/service/impl/CreateExtractService.java

**Caminho completo**: `/Users/thyagoluciano/Downloads/dup-canal-participante-api/src/main/java/br/com/nuclea/dupcanalparticipanteapi/domain/extract/service/impl/CreateExtractService.java`

```java
package br.com.nuclea.dupcanalparticipanteapi.domain.extract.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventMessagingGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("extract")
@RequiredArgsConstructor
public class CreateExtractService extends CreateService {

    @Qualifier("extract")
    private final EventDatabaseGateway eventDatabaseGateway;

    @Qualifier("extract")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("extract")
    private final EventMessagingGateway eventMessagingGateway;

    @Override
    public OperationDatabaseGateway setOperationGateway() {
        return operationDatabaseGateway;
    }

    @Override
    public EventDatabaseGateway setEventGateway() {
        return eventDatabaseGateway;
    }

    @Override
    public EventMessagingGateway setMessageGateway() {
        return eventMessagingGateway;
    }
}

```

