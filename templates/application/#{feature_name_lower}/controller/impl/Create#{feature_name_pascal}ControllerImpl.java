// template path: application/#{feature_name_lower}/controller/impl/Create#{feature_name_pascal}ControllerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.common.mapper.CreateResponseMapper;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.request.Create#{feature_name_pascal}Request;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.Create#{feature_name_pascal}Controller;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.CreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ConditionalOnProperty(name = "feat.#{feature_name_kebab}.criacao.ativo", havingValue = "true")
@RequiredArgsConstructor
public class Create#{feature_name_pascal}ControllerImpl implements Create#{feature_name_pascal}Controller {

    @Qualifier("#{feature_name_lower}")
    private final CreateService service;
    private final CreateResponseMapper mapper;

    @Override
    public ResponseEntity<CreateResponse> execute(String adminId, Create#{feature_name_pascal}Request request) {
        Operation operation = this.service.execute(adminId, request.toJson());

        CreateResponse response = mapper.toResponse(operation);

        return ResponseEntity.accepted().body(response);
    }
}