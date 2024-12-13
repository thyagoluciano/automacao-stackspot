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