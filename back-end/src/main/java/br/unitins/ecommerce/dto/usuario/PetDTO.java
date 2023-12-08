package br.unitins.ecommerce.dto.usuario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetDTO (

    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotBlank(message = "Campo especie não pode estar vazio")
    String especie,

    @NotBlank(message = "Campo raca não pode estar vazio")
    String raca,

    @NotNull(message = "Campo idadeEmMeses não pode estar vazio.")
    @Min(1)
    Integer idadeEmMeses
) {

}
