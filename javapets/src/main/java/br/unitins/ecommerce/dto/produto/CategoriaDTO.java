package br.unitins.ecommerce.dto.produto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,
    @NotBlank(message = "Campo nome não pode estar vazio")
    String descricao
) {

}
