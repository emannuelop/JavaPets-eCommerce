package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.ProdutoDTO;
import br.unitins.ecommerce.dto.produto.ProdutoResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Produto;
import jakarta.validation.Valid;

public interface ProdutoService {

    // Metodos basicos

    List<ProdutoResponseDTO> getAll(int page, int pageSize);
     byte[] createReportProdutos(String filterNome);
      byte[] gerarPdf(List<Produto> produtos);
    ProdutoResponseDTO getById(Long id);

    ProdutoResponseDTO insert(@Valid ProdutoDTO produtoDto);

    ProdutoResponseDTO update(Long id, @Valid ProdutoDTO produtoDto);

    void update(Long id, String nomeImagem);

    void delete(Long id);

    // Metodos extras

    Long count();

    Long countByNome(String nome);

    List<ProdutoResponseDTO> getByNome(String nome, int page, int pageSize);

    ProdutoResponseDTO salveImage(Long id, String nomeImagem);

    // List<ProdutoResponseDTO> getByMarca(String nome);

    // metodos de filtragem

    // List<ProdutoResponseDTO> filterByPrecoMin(Double preco);

    // List<ProdutoResponseDTO> filterByPrecoMax(Double preco);

    // List<ProdutoResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax);

}
