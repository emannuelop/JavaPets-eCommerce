package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.ProdutoDTO;
import br.unitins.ecommerce.dto.produto.ProdutoResponseDTO;

public interface ProdutoService {

    // Metodos basicos

    List<ProdutoResponseDTO> getAll(int page, int pageSize);

    ProdutoResponseDTO getById(Long id);

    ProdutoResponseDTO insert(ProdutoDTO produtoDto);

    ProdutoResponseDTO update(Long id, ProdutoDTO produtoDto);

    void update(Long id, String nomeImagem);

    void delete(Long id);

    // Metodos extras

    Long count();

    Long countByNome(String nome);

    List<ProdutoResponseDTO> getByNome(String nome, int page, int pageSize);

    // List<ProdutoResponseDTO> getByMarca(String nome);

    // metodos de filtragem

    // List<ProdutoResponseDTO> filterByPrecoMin(Double preco);

    // List<ProdutoResponseDTO> filterByPrecoMax(Double preco);

    // List<ProdutoResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax);

}
