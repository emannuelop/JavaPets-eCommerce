package br.unitins.ecommerce.service.usuario;

import java.util.List;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;

public interface UsuarioService {
    
    // Metodos basicos

    List<UsuarioResponseDTO> getAll();
    
    UsuarioResponseDTO getById(Long id);

    UsuarioResponseDTO insert(UsuarioDTO usuarioDto);

    UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDto);

    void updateTelefone(Long id, TelefoneDTO telefoneDTO);

    void updateEndereco(Long id, EnderecoDTO enderecoDTO);

    void delete(Long id);

    void insertListaDesejo(ListaDesejoDTO listaDto);

    ListaDesejoResponseDTO getListaDesejo(Long id);

    void deleteProdutoFromListaDesejo(Long id, Long idProduto);

    void deleteProdutoFromListaDesejo(Produto produto);

    // Metodos extras

    Long count();

    List<UsuarioResponseDTO> getByNome(String nome);

    Usuario getByLoginAndSenha(String login, String senha);

    Integer countListaDesejo(Long id);
}
