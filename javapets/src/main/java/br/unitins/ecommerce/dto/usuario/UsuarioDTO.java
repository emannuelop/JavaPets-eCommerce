package br.unitins.ecommerce.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;

public record UsuarioDTO(

    @NotBlank(message = "o campo login não pode estar nulo")
    @Size(max=20, message= "O campo login não pode ter mais de 20 caracteres")
    String login,

    @NotBlank(message = "O campo senha não pode estar nulo")
    String senha,

    @NotNull(message = "O campo pessoa não pode estar nulo")
    PessoaFisicaDTO pessoaFisicaDto,

    @NotNull(message = "O campo endereco não pode estar nulo")
    EnderecoDTO endereco,

    @NotNull(message = "O campo telefones não pode estar nulo")
    ArrayList<TelefoneDTO> telefones
    
) {
    
}