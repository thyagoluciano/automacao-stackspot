// template path: application/#{feature_name_lower}/controller/Create#{feature_name_pascal}Controller.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.controller;

import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupCommandResponse;
import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupQueryResponseError;
import br.com.nuclea.dupcanalparticipanteapi.application.common.response.CreateResponse;
import br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.request.Create#{feature_name_pascal}Request;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface Create#{feature_name_pascal}Controller {
    @Operation(
            tags = {"#{feature_name_kebab}"},
            operationId = "create-#{feature_name_kebab}-request",
            summary = "#{feature_name_upper}, Solicitação de #{feature_name_pascal}",
            description = "Assincrono, um id sera gerado para realizar polling no endpoint de retorno",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito - Requisição em processamento.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupCommandResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Erro ao processar requisição.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Dados do cabeçalho incorretos.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "412", description = "Precondiction Failed - Dados da requisição inconsistentes.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class))})
            }
    )
    @PostMapping("#{create_resource_feature_name_kebab}")
    ResponseEntity<CreateResponse> execute(
            @RequestHeader("X-CIP-Party-Admin-Id") @Pattern(regexp = "^[A-Za-z0-9-]+$") String adminId,
            @RequestBody @Valid Create#{feature_name_pascal}Request request
    );
}