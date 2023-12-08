package br.unitins.ecommerce.dto.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.model.endereco.Estado;
import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.usuario.Perfil;
import br.unitins.ecommerce.model.usuario.PessoaFisica;
import br.unitins.ecommerce.model.usuario.Sexo;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;

public record UsuarioResponseDTOPerfis(
        Long id,
        String login,
        PessoaFisicaDTO pessoaFisicaDto,
        String nomeImagem,
        Endereco endereco,
        List<Map<String, Object>> telefones,
        Set<Perfil> perfis) {

    public UsuarioResponseDTOPerfis(Usuario usuario) {

        this(usuario.getId(),
                usuario.getLogin(),
                getPessoaDto(usuario.getPessoaFisica()),
                usuario.getNomeImagem(),
                usuario.getEndereco(),
                        usuario.getTelefones().stream()
                        .map(telefone -> viewTelefone(telefone.getCodigoArea(), telefone.getNumero()))
                        .collect(Collectors.toList()),
                        usuario.getPerfis());
    }

    public static UsuarioResponseDTOPerfis valueOf(Usuario usuario){
        if (usuario == null)
            return null;

        return new UsuarioResponseDTOPerfis(
            usuario.getId(),
                usuario.getLogin(),
                getPessoaDto(usuario.getPessoaFisica()),
                usuario.getNomeImagem(),
                usuario.getEndereco(),
                        usuario.getTelefones().stream()
                        .map(telefone -> viewTelefone(telefone.getCodigoArea(), telefone.getNumero()))
                        .collect(Collectors.toList()),
                        usuario.getPerfis());
    }

    private static  PessoaFisicaDTO getPessoaDto(PessoaFisica pessoa) {
        
        PessoaFisicaDTO pessoaf = new PessoaFisicaDTO(pessoa.getNome(), pessoa.getCpf(), pessoa.getEmail(), pessoa.getSexo().getId());

        return pessoaf;
    }

    // private static EnderecoDTO getEndereco(Endereco endereco) {
        
    //     EnderecoDTO endereco2 = new EnderecoDTO(endereco.getLogradouro(), 
    //     endereco.getBairro(), endereco.getNumero(), endereco.getComplemento(),
    //      endereco.getCep(), endereco.getCidade());

    //     return endereco2;
    // }

     private static String getSexoLabel(Sexo sexo) {
        return (sexo != null) ? sexo.getLabel() : "Não especificado"; // Ou outro valor padrão adequado
    }

    public static Map<String, Object> viewEndereco(String logradouro, String bairro, String numero, String complemento,
            String cep, Cidade cidade) {

        Map<String, Object> endereco = new HashMap<>();

        endereco.put("logradouro", logradouro);
        endereco.put("bairro", bairro);
        endereco.put("numero", numero);
        endereco.put("complemento", complemento);
        endereco.put("cep", cep);
        endereco.put("cidade", cidade);

        return endereco;
    }
    


    // public static Map<String, Object> viewCidade(String nome, Estado estado) {

    //     Map<String, Object> cidade = new HashMap<>();

    //     cidade.put("nome", nome);
    //     cidade.put("estado", CidadeResponseDTO.viewEstado(estado.getNome(), estado.getSigla()));

    //     return cidade;
    // }

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