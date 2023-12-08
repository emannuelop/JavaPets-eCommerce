package br.unitins.ecommerce.dto.telefone;

import jakarta.validation.constraints.NotBlank;

public record TelefoneDTO(
    @NotBlank(message= "O campo codigo de area não pode estar vazio")
    String codigoArea,
    
    @NotBlank(message= "O campo número não pode estar vazio")
    String numero
) {
    
}
