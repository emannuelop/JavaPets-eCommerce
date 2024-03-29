package br.unitins.ecommerce.dto.produto;

import br.unitins.ecommerce.model.produto.produto.Fornecedor;


public record FornecedorResponseDTO (
    Long id,
    String nome,
    String email
) {

    public FornecedorResponseDTO(Fornecedor fornecedor) {
        
        this(fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getEmail());
    }

}
