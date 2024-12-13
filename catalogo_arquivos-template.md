## adapter/#{feature_name_lower}/database/#{feature_name_pascal}EventDatabaseAdapter.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/database/#{feature_name_pascal}EventDatabaseAdapter.java`

```java
// template path: adapter/#{feature_name_lower}/database/#{feature_name_pascal}EventDatabaseAdapter.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}EventEntity;
import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.repository.#{feature_name_pascal}EventRepository;
import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.mapper.#{feature_name_pascal}EventEntityMapper;
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
@Qualifier("#{feature_name_lower}")
public class #{feature_name_pascal}EventDatabaseAdapter implements EventDatabaseGateway {

    private final #{feature_name_pascal}EventRepository repository;
    private final #{feature_name_pascal}EventEntityMapper mapper;

    @Override
    public void save(Event event) {
        #{feature_name_pascal}EventEntity entity = mapper.toEntity(event);

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

        #{feature_name_pascal}EventEntity entity = repository.findByOperationId(operationId);

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

## adapter/#{feature_name_lower}/database/#{feature_name_pascal}OperationDatabaseAdapter.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/database/#{feature_name_pascal}OperationDatabaseAdapter.java`

```java
// template path: adapter/#{feature_name_lower}/database/#{feature_name_pascal}OperationDatabaseAdapter.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}OperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.repository.#{feature_name_pascal}OperationRepository;
import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.mapper.#{feature_name_pascal}OperationEntityMapper;
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
@Qualifier("#{feature_name_lower}")
public class #{feature_name_pascal}OperationDatabaseAdapter implements OperationDatabaseGateway {

    private final #{feature_name_pascal}OperationRepository repository;
    private final #{feature_name_pascal}OperationEntityMapper mapper;

    @Override
    public void save(Operation operation) {
        #{feature_name_pascal}OperationEntity entity = mapper.toEntity(operation);

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

        #{feature_name_pascal}OperationEntity entity = repository.findById(id).orElse(null);

        return mapper.toModel(entity);
    }

}
```

## adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}OperationRepository.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}OperationRepository.java`

```java
// template path: adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}OperationRepository.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}OperationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface #{feature_name_pascal}OperationRepository extends JpaRepository<#{feature_name_pascal}OperationEntity, UUID> {
}
```

## adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}EventRepository.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}EventRepository.java`

```java
// template path: adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}EventRepository.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface #{feature_name_pascal}EventRepository extends JpaRepository<#{feature_name_pascal}EventEntity, UUID> {
    #{feature_name_pascal}EventEntity findByOperationId(UUID operationId);
}
```

## adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}OperationEntity.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}OperationEntity.java`

```java
// template path: adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}OperationEntity.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity;

import br.com.nuclea.dupcanalparticipanteapi.adapter.generic.database.OperationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "${table_name_op}")
public class #{feature_name_pascal}OperationEntity extends OperationEntity {

}
```

## adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}EventEntity.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}EventEntity.java`

```java
// template path: adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}EventEntity.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity;

import br.com.nuclea.dupcanalparticipanteapi.adapter.generic.database.EventEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "#{table_name_evt}")
@Entity
public class #{feature_name_pascal}EventEntity extends EventEntity {
}
```

## adapter/#{feature_name_lower}/producer/#{feature_name_pascal}EventMessagingAdapter.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/producer/#{feature_name_pascal}EventMessagingAdapter.java`

```java
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
```

## adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}OperationEntityMapper.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}OperationEntityMapper.java`

```java
// template path: adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}OperationEntityMapper.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}OperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface #{feature_name_pascal}OperationEntityMapper {
    #{feature_name_pascal}OperationEntity toEntity(Operation operation);

    Operation toModel(#{feature_name_pascal}OperationEntity entity);
}
```

## adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}EventEntityMapper.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}EventEntityMapper.java`

```java
// template path: adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}EventEntityMapper.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}EventEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.model.Event;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface #{feature_name_pascal}EventEntityMapper {
    #{feature_name_pascal}EventEntity toEntity(Event model);

    Event toModel(#{feature_name_pascal}EventEntity entity);
}
```

## application/#{feature_name_lower}/controller/Create#{feature_name_pascal}Controller.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/controller/Create#{feature_name_pascal}Controller.java`

```java
// template path: application/#{feature_name_lower}/controller/Create#{feature_name_pascal}Controller.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller;

import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupCommandResponse;
import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupQueryResponseError;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.request.Create#{feature_name_pascal}Request;
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

public interface Create#{feature_name_pascal}Controller {
    @Operation(
            tags = {"#{feature_name_kebab}"},
            operationId = "create-#{feature_name_kebab}-request",
            summary = "#{feature_name_upper}, Solicitação de #{feature_name_pascal}",
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
    @PostMapping("${create_resource_feature_name_kebab}")
    ResponseEntity<CreateResponse> execute(
            @RequestHeader("X-CIP-Party-Admin-Id") @Pattern(regexp = "^[A-Za-z0-9-]+$") String adminId,
            @RequestBody @Valid Create#{feature_name_pascal}Request request
    );
}
```

## application/#{feature_name_lower}/controller/Query#{feature_name_pascal}Controller.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/controller/Query#{feature_name_pascal}Controller.java`

```java
// template path: application/#{feature_name_lower}/controller/Query#{feature_name_pascal}Controller.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller;

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

public interface Query#{feature_name_pascal}Controller {

    @Operation(
            tags = {"query-#{feature_name_kebab}"},
            operationId = "query-#{feature_name_kebab}-request",
            summary = "#{feature_name_upper}, Solicitar #{feature_name_pascal}",
            description = "Assincrono, #{feature_name_upper}",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Consulta sem resultado.", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Erro ao processar requisição.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Dados do cabeçalho incorretos.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "404", description = "Not Found.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "412", description = "Precondiction Failed - Dados da requisição inconsistentes.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) })
            }
    )
    @PostMapping(value = "#{query_resource_feature_name_kebab}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> execute(
            @RequestHeader("X-CIP-Party-Admin-Id") @Pattern(regexp = "^[A-Za-z0-9-]+$") String adminId,
            @RequestBody @Valid QueryRequest request
    ) throws Exception;
}
```

## application/#{feature_name_lower}/controller/impl/Query#{feature_name_pascal}ControllerImpl.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/controller/impl/Query#{feature_name_pascal}ControllerImpl.java`

```java
// template path: application/#{feature_name_lower}/controller/impl/Query#{feature_name_pascal}ControllerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.exchangeAct.request.QueryRequest;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.Query#{feature_name_pascal}Controller;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "feat.#{feature_name_kebab}.consulta.ativo", havingValue = "true")
public class Query#{feature_name_pascal}ControllerImpl implements Query#{feature_name_pascal}Controller {

    private final QueryService service;

    public Query#{feature_name_pascal}ControllerImpl(@Qualifier("#{feature_name_lower}") QueryService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> execute(String adminId, QueryRequest request) throws Exception {
        String payload = service.execute(adminId, request.getOperationId());

        return ResponseEntity.ok(payload);
    }
}
```

## application/#{feature_name_lower}/controller/impl/Create#{feature_name_pascal}ControllerImpl.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/controller/impl/Create#{feature_name_pascal}ControllerImpl.java`

```java
// template path: application/#{feature_name_lower}/controller/impl/Create#{feature_name_pascal}ControllerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.common.mapper.CreateResponseMapper;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.request.Create#{feature_name_pascal}Request;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.Create#{feature_name_pascal}Controller;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ConditionalOnProperty(name = "feat.#{feature_name_kebab}.criacao.ativo", havingValue = "true")
@RequiredArgsConstructor
public class Create#{feature_name_pascal}ControllerImpl implements Create#{feature_name_pascal}Controller {

    @Qualifier("#{feature_name_lower}")
    private final CreateService service;
    private final CreateResponseMapper mapper;

    @Override
    public ResponseEntity<CreateResponse> execute(String adminId, Create#{feature_name_pascal}Request request) {
        Operation operation = this.service.execute(adminId, request.toJson());

        CreateResponse response = mapper.toResponse(operation);

        return ResponseEntity.accepted().body(response);
    }
}
```

## application/#{feature_name_lower}/request/Create#{feature_name_pascal}Request.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/request/Create#{feature_name_pascal}Request.java`

```java
// template path: application/#{feature_name_lower}/request/Create#{feature_name_pascal}Request.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.request;

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
public class Create#{feature_name_pascal}Request {

    ${payload_fields}

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

## application/#{feature_name_lower}/consumer/Reply#{feature_name_pascal}Consumer.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/consumer/Reply#{feature_name_pascal}Consumer.java`

```java
// template path: application/#{feature_name_lower}/consumer/Reply#{feature_name_pascal}Consumer.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.consumer;

import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;

public interface Reply#{feature_name_pascal}Consumer {
    void execute(String payload, ConsumerRecordMetadata metadata);
}
```

## application/#{feature_name_lower}/consumer/impl/Reply#{feature_name_pascal}ConsumerImpl.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/application/#{feature_name_lower}/consumer/impl/Reply#{feature_name_pascal}ConsumerImpl.java`

```java
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
```

## domain/#{feature_name_lower}/service/impl/Query#{feature_name_pascal}ServiceImpl.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/domain/#{feature_name_lower}/service/impl/Query#{feature_name_pascal}ServiceImpl.java`

```java
// template path: domain/#{feature_name_lower}/service/impl/Query#{feature_name_pascal}ServiceImpl.java
package br.com.nuclea.dupcanalparticipanteapi.domain.#{feature_name_lower}.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("#{feature_name_lower}")
@RequiredArgsConstructor
public class Query#{feature_name_pascal}ServiceImpl extends QueryService {

    @Qualifier("#{feature_name_lower}")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("#{feature_name_lower}")
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

## domain/#{feature_name_lower}/service/impl/Reply#{feature_name_pascal}ServiceImpl.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/domain/#{feature_name_lower}/service/impl/Reply#{feature_name_pascal}ServiceImpl.java`

```java
// template path: domain/#{feature_name_lower}/service/impl/Reply#{feature_name_pascal}ServiceImpl.java
package br.com.nuclea.dupcanalparticipanteapi.domain.#{feature_name_lower}.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("#{feature_name_lower}")
@RequiredArgsConstructor
public class Reply#{feature_name_pascal}ServiceImpl extends ReplyService {

    @Qualifier("#{feature_name_lower}")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("#{feature_name_lower}")
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

## domain/#{feature_name_lower}/service/impl/Create#{feature_name_pascal}Service.java

**Caminho completo**: `/Users/thyagoluciano/Developer/zup/nuclea/automacao-stackspot/templates/domain/#{feature_name_lower}/service/impl/Create#{feature_name_pascal}Service.java`

```java
// template path: domain/#{feature_name_lower}/service/impl/Create#{feature_name_pascal}Service.java
package br.com.nuclea.dupcanalparticipanteapi.domain.#{feature_name_lower}.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventMessagingGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("#{feature_name_lower}")
@RequiredArgsConstructor
public class Create#{feature_name_pascal}Service extends CreateService {

    @Qualifier("#{feature_name_lower}")
    private final EventDatabaseGateway eventDatabaseGateway;

    @Qualifier("#{feature_name_lower}")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("#{feature_name_lower}")
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

