// template path: application/#{feature_name_lower}/controller/impl/Query#{feature_name_pascal}ControllerImpl.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.impl;

import br.com.nuclea.dupcanalparticipanteapi.application.exchangeAct.request.QueryRequest;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller.Query#{feature_name_pascal}Controller;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.service.QueryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name = "feat.#{feature_name_kebab}.consulta.ativo", havingValue = "true")
public class Query#{feature_name_pascal}ControllerImpl implements Query#{feature_name_pascal}Controller {

    private final QueryService service;

    public Query#{feature_name_pascal}ControllerImpl(@Qualifier("#{feature_name_lower}") QueryService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> execute(String adminId, QueryRequest request) throws Exception {
        String payload = service.execute(adminId, request.getOperationId());

        return ResponseEntity.ok(payload);
    }
}