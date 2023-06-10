package br.unitins.ecommerce.dto.usuario;

import br.unitins.ecommerce.model.usuario.Usuario;

public record UsuarioBasicoResponseDTO(
        Long id,
        String login,
        String nome,
        String email
) {

    public UsuarioBasicoResponseDTO(Usuario usuario) {

        this(usuario.getId(),
                usuario.getLogin(),
                usuario.getPessoaFisica().getNome(),
                usuario.getPessoaFisica().getEmail());
    }

}