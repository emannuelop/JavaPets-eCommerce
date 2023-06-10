package br.unitins.ecommerce.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioBasicoDTO(

    @NotBlank
    String login,

    @NotBlank(message = "O campo senha não pode estar nulo")
    String senha,

    @NotNull
    String nome,

    @NotNull
    String email

) {
    
}