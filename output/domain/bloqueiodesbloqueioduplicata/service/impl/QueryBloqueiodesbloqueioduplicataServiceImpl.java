// template path: domain/bloqueiodesbloqueioduplicata/service/impl/QueryBloqueiodesbloqueioduplicataServiceImpl.java
package br.com.nuclea.dupcanalparticipanteapi.domain.bloqueiodesbloqueioduplicata.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Qualifier("bloqueiodesbloqueioduplicata")
@RequiredArgsConstructor
public class QueryBloqueiodesbloqueioduplicataServiceImpl extends QueryService {

    @Qualifier("bloqueiodesbloqueioduplicata")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("bloqueiodesbloqueioduplicata")
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