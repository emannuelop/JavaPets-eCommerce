package br.unitins.ecommerce.dto.municipio;

import java.util.HashMap;
import java.util.Map;

import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.model.endereco.Cidade;

public record CidadeResponseDTO(
        Long id,
        String nome,
        EstadoResponseDTO estado) {

    public static CidadeResponseDTO valueOf(Cidade cidade) {
        return new CidadeResponseDTO(
                cidade.getId(),
                cidade.getNome(),
                EstadoResponseDTO.valueOf(cidade.getEstado()));
    }

     public static Map<String, Object> viewEstado(String nome, String sigla) {

        Map<String, Object> estado = new HashMap<>();

        estado.put("nome", nome);
        estado.put("sigla", sigla);

        return estado;
    }

}
