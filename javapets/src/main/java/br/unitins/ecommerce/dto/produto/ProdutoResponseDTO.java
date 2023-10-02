package br.unitins.ecommerce.dto.produto;

import br.unitins.ecommerce.model.produto.produto.Categoria;
import br.unitins.ecommerce.model.produto.produto.Fornecedor;
import br.unitins.ecommerce.model.produto.produto.Marca;
import br.unitins.ecommerce.model.produto.produto.Produto;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    String descricao,
    Marca marca,
    Fornecedor fornecedor,
    Categoria categoria,
    Double preco,
    int estoque
) {
    
    public static ProdutoResponseDTO valueOf(Produto produto) {

        return new ProdutoResponseDTO(produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getMarca(),
            produto.getFornecedor(),
            produto.getCategoria(),
            produto.getPreco(),
            produto.getEstoque()
            );
    }
}
