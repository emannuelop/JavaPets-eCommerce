package br.unitins.ecommerce.dto.produto;

import br.unitins.ecommerce.model.produto.produto.Produto;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String nomeImagem,
    Double preco,
    String estoque,
    String nomeMarca
) {
    
    public ProdutoResponseDTO(Produto produto) {

        this(produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getNomeImagem(),
            produto.getPreco(),
            produto.getEstoque() > 0? "Disponível" : "Estoque esgotado",
            produto.getMarca().getNome()
            );
    }
}
