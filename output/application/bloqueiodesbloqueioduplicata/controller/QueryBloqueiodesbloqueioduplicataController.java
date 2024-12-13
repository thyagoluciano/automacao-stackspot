// template path: application/bloqueiodesbloqueioduplicata/controller/QueryBloqueiodesbloqueioduplicataController.java
package br.com.nuclea.dupcanalparticipanteapi.application.bloqueiodesbloqueioduplicata.controller;

import br.com.nuclea.canal.participante.dupcanalparticipanteapi.controller.response.DupQueryResponseError;
import br.com.nuclea.dupcanalparticipanteapi.application.exchangeAct.request.QueryRequest;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface QueryBloqueiodesbloqueioduplicataController {

    @Operation(
            tags = {"query-bloqueiodesbloqueioduplicata"},
            operationId = "query-bloqueiodesbloqueioduplicata-request",
            summary = "BLOQUEIODESBLOQUEIODUPLICATA, Solicitar Bloqueiodesbloqueioduplicata",
            description = "Assincrono, BLOQUEIODESBLOQUEIODUPLICATA",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Consulta sem resultado.", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Erro ao processar requisição.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Dados do cabeçalho incorretos.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "404", description = "Not Found.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "412", description = "Precondiction Failed - Dados da requisição inconsistentes.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro inesperado.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DupQueryResponseError.class)) })
            }
    )
    @PostMapping(value = "/escrituradora/duplicata/bloquear-desbloquear/retorno", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> execute(
            @RequestHeader("X-CIP-Party-Admin-Id") @Pattern(regexp = "^[A-Za-z0-9-]+$") String adminId,
            @RequestBody @Valid QueryRequest request
    ) throws Exception;
}