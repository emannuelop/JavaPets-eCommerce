package br.unitins.ecommerce.dto.compra;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CupomDescontoDTO (
    @NotBlank(message = "Campo codigoCupom não pode estar vazio.")
    String codigoCupom,

    @NotNull(message = "Campo quantidadeDisponivel não pode estar vazio.")
    @Min(1)
    int quantidadeDisponivel,

    @NotNull(message = "Campo porcentagemDesconto não pode estar vazio.")
    int porcentagemDesconto
) {

}
