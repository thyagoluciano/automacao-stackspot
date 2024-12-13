// template path: application/bloqueiodesbloqueioduplicata/controller/impl/QueryBloqueiodesbloqueioduplicataControllerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.exchangeAct.request.QueryRequest;
import br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.controller.QueryBloqueiodesbloqueioduplicataController;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "feat.bloqueiodesbloqueioduplicata.consulta.ativo", havingValue = "true")
public class QueryBloqueiodesbloqueioduplicataControllerImpl implements QueryBloqueiodesbloqueioduplicataController {

    private final QueryService service;

    public QueryBloqueiodesbloqueioduplicataControllerImpl(@Qualifier("bloqueiodesbloqueioduplicata") QueryService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> execute(String adminId, QueryRequest request) throws Exception {
        String payload = service.execute(adminId, request.getOperationId());

        return ResponseEntity.ok(payload);
    }
}