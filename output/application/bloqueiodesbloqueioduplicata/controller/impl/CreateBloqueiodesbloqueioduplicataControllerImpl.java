// template path: application/bloqueiodesbloqueioduplicata/controller/impl/CreateBloqueiodesbloqueioduplicataControllerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.common.mapper.CreateResponseMapper;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.request.CreateBloqueiodesbloqueioduplicataRequest;
import br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.controller.CreateBloqueiodesbloqueioduplicataController;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ConditionalOnProperty(name = "feat.bloqueiodesbloqueioduplicata.criacao.ativo", havingValue = "true")
@RequiredArgsConstructor
public class CreateBloqueiodesbloqueioduplicataControllerImpl implements CreateBloqueiodesbloqueioduplicataController {

    @Qualifier("bloqueiodesbloqueioduplicata")
    private final CreateService service;
    private final CreateResponseMapper mapper;

    @Override
    public ResponseEntity<CreateResponse> execute(String adminId, CreateBloqueiodesbloqueioduplicataRequest request) {
        Operation operation = this.service.execute(adminId, request.toJson());

        CreateResponse response = mapper.toResponse(operation);

        return ResponseEntity.accepted().body(response);
    }
}