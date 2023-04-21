package br.unitins.ecommerce.dto.telefone;

import javax.validation.constraints.NotBlank;

public record TelefoneDTO(
    @NotBlank(message = "O campo código de área não pode ser nulo")
    String codigoArea,

    @NotBlank(message = "O campo código de área não pode ser nulo")
    String numero
) {
    
}
