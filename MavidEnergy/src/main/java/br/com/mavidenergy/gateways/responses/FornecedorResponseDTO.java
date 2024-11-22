package br.com.mavidenergy.gateways.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FornecedorResponseDTO {
    private String nomeFornecedor;
    private String cnpj;
    private String telefone;
    private String email;
    private EnderecoResponseDTO endereco;
    private Double distancia;
}
