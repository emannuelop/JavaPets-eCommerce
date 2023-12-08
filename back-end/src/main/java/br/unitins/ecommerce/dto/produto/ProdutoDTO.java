package br.unitins.ecommerce.dto.produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotBlank(message = "Campo descricao não pode estar vazio")
    String descricao,

    @NotNull(message = "Campo idmarca não pode estar vazio")
    @Min(1)
    Long idMarca,

    @NotNull(message = "Campo idfornecedor não pode estar vazio")
    @Min(1)
    Long idFornecedor,

    @NotNull(message = "Campo idcategoria não pode estar vazio")
    @Min(1)
    Long idCategoria,

    @NotNull(message = "Campo preço não pode estar vazio")
    @Min(1)
    Double preco,

    @NotNull(message = "Campo estoque não pode estar vazio")
    @Min(0)
    Integer estoque
) {
    
}
