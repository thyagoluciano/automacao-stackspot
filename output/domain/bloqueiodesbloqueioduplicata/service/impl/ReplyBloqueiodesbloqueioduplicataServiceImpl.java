// template path: domain/bloqueiodesbloqueioduplicata/service/impl/ReplyBloqueiodesbloqueioduplicataServiceImpl.java
package br.com.nuclea.dupcanalparticipanteapi.domain.bloqueiodesbloqueioduplicata.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bloqueiodesbloqueioduplicata")
@RequiredArgsConstructor
public class ReplyBloqueiodesbloqueioduplicataServiceImpl extends ReplyService {

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