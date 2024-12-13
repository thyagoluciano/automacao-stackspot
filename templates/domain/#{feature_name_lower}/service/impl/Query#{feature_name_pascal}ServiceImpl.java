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