package br.unitins.ecommerce.dto.compra;

import jakarta.validation.constraints.NotBlank;

public record CupomDescontoDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String codigoCupom,

    int quantidadeDisponivel,

    int porcentagemDesconto
) {

}
