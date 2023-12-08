package br.unitins.ecommerce.dto.municipio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CidadeDTO(
    @NotBlank(message = "O campo nome deve ser informado.")
     @Size(min=3, message ="O campo nome deve possuir no mínimo 3 caracteres")
    String nome,

    @NotNull(message = "O campo idEstado deve ser informado.")
    Long idEstado
) { }
