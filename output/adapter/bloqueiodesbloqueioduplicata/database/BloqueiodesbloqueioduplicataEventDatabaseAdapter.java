// template path: adapter/bloqueiodesbloqueioduplicata/database/BloqueiodesbloqueioduplicataEventDatabaseAdapter.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database;

import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.entity.BloqueiodesbloqueioduplicataEventEntity;
import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.repository.BloqueiodesbloqueioduplicataEventRepository;
import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.mapper.BloqueiodesbloqueioduplicataEventEntityMapper;
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
@Qualifier("bloqueiodesbloqueioduplicata")
public class BloqueiodesbloqueioduplicataEventDatabaseAdapter implements EventDatabaseGateway {

    private final BloqueiodesbloqueioduplicataEventRepository repository;
    private final BloqueiodesbloqueioduplicataEventEntityMapper mapper;

    @Override
    public void save(Event event) {
        BloqueiodesbloqueioduplicataEventEntity entity = mapper.toEntity(event);

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

        BloqueiodesbloqueioduplicataEventEntity entity = repository.findByOperationId(operationId);

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