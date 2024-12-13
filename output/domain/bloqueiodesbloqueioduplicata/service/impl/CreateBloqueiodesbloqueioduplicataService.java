// template path: domain/bloqueiodesbloqueioduplicata/service/impl/CreateBloqueiodesbloqueioduplicataService.java
package br.com.nuclea.dupcanalparticipanteapi.domain.bloqueiodesbloqueioduplicata.service.impl;

import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.EventMessagingGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.gateway.OperationDatabaseGateway;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bloqueiodesbloqueioduplicata")
@RequiredArgsConstructor
public class CreateBloqueiodesbloqueioduplicataService extends CreateService {

    @Qualifier("bloqueiodesbloqueioduplicata")
    private final EventDatabaseGateway eventDatabaseGateway;

    @Qualifier("bloqueiodesbloqueioduplicata")
    private final OperationDatabaseGateway operationDatabaseGateway;

    @Qualifier("bloqueiodesbloqueioduplicata")
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