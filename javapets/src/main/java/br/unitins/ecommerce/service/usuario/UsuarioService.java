package br.unitins.ecommerce.service.usuario;

import java.util.List;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.UpgradeUsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioBasicoDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.dadospessoais.DadosPessoaisDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;

public interface UsuarioService {

    // Metodos basicos

    List<UsuarioResponseDTO> getAllUsuario();

    public List<UsuarioBasicoResponseDTO> getAllUsuarioBasico();

    UsuarioResponseDTO getById(Long id);

    UsuarioResponseDTO insert(UsuarioDTO usuarioDto);

    UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDto);

    void delete(Long id);

    void insertListaDesejo(ListaDesejoDTO listaDto);

    ListaDesejoResponseDTO getListaDesejo(Long id);

    void deleteProdutoFromListaDesejo(Long id, Long idProduto);

    void deleteProdutoFromListaDesejo(Produto produto);

    // Metodos extras

    Long count();

    List<UsuarioResponseDTO> getByNome(String nome);

    Usuario getByLoginAndSenha(String login, String senha);

    Usuario getByLogin(String login);

    void update(Long id, DadosPessoaisDTO dadosPessoaisDTO);

    void update(Long id, SenhaDTO senhaDTO);

    void update(Long id, EnderecoDTO enderecoDTO);

    void update(Long id, String nomeImagem);

    void insertTelefone (Long idUsuario, TelefoneDTO telefoneDTO);

    void removeTelefone (Long idUsuario, Long idTelefone);

    Integer countListaDesejo(Long id);

    // Usuario basico

    UsuarioBasicoResponseDTO insert(UsuarioBasicoDTO usuarioBasicoDto);

    UsuarioResponseDTO upgrade(Long id, UpgradeUsuarioDTO usuarioDto);
}
