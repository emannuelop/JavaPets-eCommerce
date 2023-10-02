package br.unitins.ecommerce.dto.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
import br.unitins.ecommerce.model.endereco.Estado;
import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String login,
        String email,
        String cpf,
        String nomeImagem,
        Map<String, Object> endereco,
        List<Map<String, Object>> telefones) {

    public UsuarioResponseDTO(Usuario usuario) {

        this(usuario.getId(),
                usuario.getPessoaFisica().getNome(),
                usuario.getLogin(),
                usuario.getPessoaFisica().getEmail(),
                usuario.getPessoaFisica().getCpf(),
                usuario.getNomeImagem(),
                viewEndereco(usuario.getEndereco().getLogradouro(),
                        usuario.getEndereco().getBairro(),
                        usuario.getEndereco().getNumero(),
                        usuario.getEndereco().getComplemento(),
                        usuario.getEndereco().getCep(),
                        usuario.getEndereco().getCidade()),
                        viewItensCompra(usuario.getTelefones()));
    }

    public static Map<String, Object> viewEndereco(String logradouro, String bairro, String numero, String complemento,
            String cep, Cidade municipio) {

        Map<String, Object> endereco = new HashMap<>();

        endereco.put("logradouro", logradouro);
        endereco.put("bairro", bairro);
        endereco.put("numero", numero);
        endereco.put("complemento", complemento);
        endereco.put("cep", cep);
        endereco.put("municipio", viewCidade(municipio.getNome(), municipio.getEstado()));

        return endereco;
    }

    public static Map<String, Object> viewCidade(String nome, Estado estado) {

        Map<String, Object> municipio = new HashMap<>();

        municipio.put("nome", nome);
        municipio.put("estado", CidadeResponseDTO.viewEstado(estado.getNome(), estado.getSigla()));

        return municipio;
    }

    public static Map<String, Object> viewTelefone(String codigoArea, String numero) {

        Map<String, Object> telefone = new HashMap<>();

        telefone.put("codigoDeArea", codigoArea);
        telefone.put("numero", numero);

        return telefone;
    }

    private static List<Map<String, Object>> viewItensCompra (List<Telefone> lista) {

        List<Map<String, Object>> listaitensCompra = new ArrayList<>();

        for (Telefone itensCompra : lista) {
            
            Map<String, Object> itemCompra = new HashMap<>();

            itemCompra = viewTelefone(itensCompra.getCodigoArea(), itensCompra.getNumero());

            listaitensCompra.add(itemCompra);
        }

        return listaitensCompra;
    }
}