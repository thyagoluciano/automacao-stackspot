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