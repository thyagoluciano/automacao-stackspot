import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa o payload de requisição para o endpoint de bloqueio/desbloqueio de duplicatas.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Create#{feature_name_pascal}Request {

    /**
     * Tipo de Pessoa do Sacador.
     * Valores permitidos: "F" (Física) ou "J" (Jurídica).
     */
    @NotNull(message = "O campo 'tipoPessoaSacador' é obrigatório.")
    @Pattern(regexp = "F|J", message = "O campo 'tipoPessoaSacador' deve ser 'F' ou 'J'.")
    private String tipoPessoaSacador;

    /**
     * CPF ou CNPJ do Sacador.
     * Deve conter apenas números, com tamanho entre 11 e 14 caracteres.
     */
    @NotNull(message = "O campo 'cpfOuCnpjSacador' é obrigatório.")
    @Pattern(regexp = "^[0-9]*[1-9]+0*$", message = "O campo 'cpfOuCnpjSacador' deve conter apenas números válidos.")
    @Size(min = 11, max = 14, message = "O campo 'cpfOuCnpjSacador' deve ter entre 11 e 14 caracteres.")
    private String cpfOuCnpjSacador;

    /**
     * Ação de Bloqueio ou Desbloqueio.
     * Valores permitidos: "B" (Bloqueio) ou "D" (Desbloqueio).
     */
    @NotNull(message = "O campo 'acaoBloqueioOuDesbloqueio' é obrigatório.")
    @Pattern(regexp = "B|D", message = "O campo 'acaoBloqueioOuDesbloqueio' deve ser 'B' ou 'D'.")
    private String acaoBloqueioOuDesbloqueio;

    /**
     * Código de Identificação da Duplicata.
     * Deve conter exatamente 20 caracteres alfanuméricos.
     */
    @NotNull(message = "O campo 'codigoIdentificacaoDuplicata' é obrigatório.")
    @Pattern(regexp = "^[a-zA-Z0-9]{20}$", message = "O campo 'codigoIdentificacaoDuplicata' deve conter exatamente 20 caracteres alfanuméricos.")
    private String codigoIdentificacaoDuplicata;

    /**
     * Descrição do Motivo de Bloqueio ou Desbloqueio.
     * Tamanho máximo: 80 caracteres.
     */
    @Size(max = 80, message = "O campo 'descricaoMotivoBloqueioOuDesbloqueio' deve ter no máximo 80 caracteres.")
    private String descricaoMotivoBloqueioOuDesbloqueio;
}