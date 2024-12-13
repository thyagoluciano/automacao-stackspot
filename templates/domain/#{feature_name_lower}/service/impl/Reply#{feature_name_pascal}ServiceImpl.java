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