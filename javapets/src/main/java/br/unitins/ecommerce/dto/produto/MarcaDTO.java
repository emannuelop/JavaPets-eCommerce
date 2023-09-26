package br.unitins.ecommerce.dto.produto;

import jakarta.validation.constraints.NotBlank;

public record MarcaDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,
    @NotBlank(message = "Campo nome não pode estar vazio")
    String cnpj
) {

}
