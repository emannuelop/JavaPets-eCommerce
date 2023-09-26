package br.unitins.ecommerce.dto.produto;

import jakarta.validation.constraints.NotBlank;

public record PetDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,
    @NotBlank(message = "Campo nome não pode estar vazio")
    String especie,
    @NotBlank(message = "Campo nome não pode estar vazio")
    String raca,
    int idadeEmMeses
) {

}
