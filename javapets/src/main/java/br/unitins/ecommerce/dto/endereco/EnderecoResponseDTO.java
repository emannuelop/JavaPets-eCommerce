package br.unitins.ecommerce.dto.endereco;

import java.util.HashMap;
import java.util.Map;

import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
import br.unitins.ecommerce.model.endereco.Endereco;

public record EnderecoResponseDTO(
    String logradouro,
    String bairro,
    String numero,
    String complemento,
    String cep,
    Map<String, Object> municipio
) {
    
    public EnderecoResponseDTO (Endereco endereco) {

        this(endereco.getLogradouro(),
            endereco.getBairro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getCep(),
            viewCidade(endereco.getCidade().getNome(),
                            endereco.getCidade().getEstado().getNome(),
                            endereco.getCidade().getEstado().getSigla()));
    }

    private static Map<String, Object> viewCidade(String nome, String nomeEstado, String siglaEstado) {

        Map<String, Object> municipio = new HashMap<>();

        municipio.put("estado", CidadeResponseDTO.viewEstado(nomeEstado, siglaEstado));
        municipio.put("nome", nome);

        return municipio;
    }
}
