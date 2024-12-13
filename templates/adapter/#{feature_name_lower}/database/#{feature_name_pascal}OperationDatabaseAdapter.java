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