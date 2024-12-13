// template path: adapter/bloqueiodesbloqueioduplicata/database/BloqueiodesbloqueioduplicataOperationDatabaseAdapter.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database;

import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.entity.BloqueiodesbloqueioduplicataOperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.repository.BloqueiodesbloqueioduplicataOperationRepository;
import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.mapper.BloqueiodesbloqueioduplicataOperationEntityMapper;
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
@Qualifier("bloqueiodesbloqueioduplicata")
public class BloqueiodesbloqueioduplicataOperationDatabaseAdapter implements OperationDatabaseGateway {

    private final BloqueiodesbloqueioduplicataOperationRepository repository;
    private final BloqueiodesbloqueioduplicataOperationEntityMapper mapper;

    @Override
    public void save(Operation operation) {
        BloqueiodesbloqueioduplicataOperationEntity entity = mapper.toEntity(operation);

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

        BloqueiodesbloqueioduplicataOperationEntity entity = repository.findById(id).orElse(null);

        return mapper.toModel(entity);
    }

}