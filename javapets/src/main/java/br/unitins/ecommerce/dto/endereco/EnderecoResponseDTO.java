package br.unitins.ecommerce.dto.endereco;

import java.util.HashMap;
import java.util.Map;

import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.model.endereco.Endereco;

public record EnderecoResponseDTO(
    String logradouro,
    String bairro,
    String numero,
    String complemento,
    String cep,
    Cidade cidade
) {
    
    public EnderecoResponseDTO (Endereco endereco) {

        this(endereco.getLogradouro(),
            endereco.getBairro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getCep(),
            endereco.getCidade());
    }

    private static Map<String, Object> viewCidade(String nome, String nomeEstado, String siglaEstado) {

        Map<String, Object> municipio = new HashMap<>();

        municipio.put("estado", CidadeResponseDTO.viewEstado(nomeEstado, siglaEstado));
        municipio.put("nome", nome);

        return municipio;
    }
}
