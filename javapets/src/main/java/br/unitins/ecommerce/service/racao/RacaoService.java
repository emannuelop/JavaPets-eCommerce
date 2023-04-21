package br.unitins.ecommerce.service.racao;

import java.util.List;

import br.unitins.ecommerce.dto.racao.RacaoDTO;
import br.unitins.ecommerce.dto.racao.RacaoResponseDTO;

public interface RacaoService {

    // Metodos basicos

    List<RacaoResponseDTO> getAll();

    RacaoResponseDTO getById(Long id);

    RacaoResponseDTO insert(RacaoDTO cafeDto);

    RacaoResponseDTO update(Long id, RacaoDTO cafeDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<RacaoResponseDTO> getByNome(String nome);

    List<RacaoResponseDTO> getByEscolhaAnimal(Integer id);

    List<RacaoResponseDTO> getByMarca(String nome);

    // metodos de filtragem

    List<RacaoResponseDTO> filterByPrecoMin(Double preco);

    List<RacaoResponseDTO> filterByPrecoMax(Double preco);

    List<RacaoResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax);

}
