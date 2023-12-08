package br.unitins.ecommerce.dto.produto;

import br.unitins.ecommerce.model.produto.produto.Marca;


public record MarcaResponseDTO (
    Long id,
    String nome,
    String cnpj
) {

    public MarcaResponseDTO(Marca marca) {
        
        this(marca.getId(),
            marca.getNome(),
            marca.getCnpj());
    }

}
