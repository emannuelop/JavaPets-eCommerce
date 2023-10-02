package br.unitins.ecommerce.dto.produto;

import jakarta.validation.constraints.NotBlank;

public record FornecedorDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome
) {

}
