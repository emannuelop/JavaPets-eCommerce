package br.unitins.ecommerce.dto.produto;

import br.unitins.ecommerce.model.produto.produto.Categoria;


public record CategoriaResponseDTO (
    Long id,
    String nome,
    String descricao
) {

    public CategoriaResponseDTO(Categoria categoria) {
        
        this(categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao());
    }

}
