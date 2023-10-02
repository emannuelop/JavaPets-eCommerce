package br.unitins.ecommerce.service.produto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import br.unitins.ecommerce.dto.produto.ProdutoDTO;
import br.unitins.ecommerce.dto.produto.ProdutoResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Produto;
import br.unitins.ecommerce.repository.CategoriaRepository;
import br.unitins.ecommerce.repository.FornecedorRepository;
import br.unitins.ecommerce.repository.MarcaRepository;
import br.unitins.ecommerce.repository.ProdutoRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@ApplicationScoped
public class ProdutoImplService implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    // @Override
    // public List<ProdutoResponseDTO> getAll() {

    //     return produtoRepository.findAll()
    //             .stream()
    //             .map(ProdutoResponseDTO::new)
    //             .toList();
    // }

     @Override
    public List<ProdutoResponseDTO> getAll(int page, int pageSize) {
        List<Produto> list = produtoRepository.findAll().page(page, pageSize).list();

        return list.stream().map(e -> ProdutoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO getById(Long id) throws NotFoundException {

        Produto produto = produtoRepository.findById(id);

        if (produto == null)
            throw new NotFoundException("Não encontrado");

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO insert(ProdutoDTO produtoDto) throws ConstraintViolationException {

        validar(produtoDto);

        Produto entity = new Produto();

        entity.setNome(produtoDto.nome());

        entity.setDescricao(produtoDto.descricao());

        entity.setMarca(marcaRepository.findById(produtoDto.idMarca()));

        entity.setFornecedor(fornecedorRepository.findById(produtoDto.idFornecedor()));

        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));

        entity.setPreco(produtoDto.preco());

        entity.setEstoque(produtoDto.estoque());

        produtoRepository.persist(entity);

        return ProdutoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(Long id, ProdutoDTO produtoDto) throws ConstraintViolationException {

        validar(produtoDto);

        Produto entity = produtoRepository.findById(id);

        entity.setNome(produtoDto.nome());

        entity.setDescricao(produtoDto.descricao());

        entity.setMarca(marcaRepository.findById(produtoDto.idMarca()));

        entity.setFornecedor(fornecedorRepository.findById(produtoDto.idFornecedor()));

        entity.setCategoria(categoriaRepository.findById(produtoDto.idCategoria()));

        entity.setPreco(produtoDto.preco());

        entity.setEstoque(produtoDto.estoque());

        return ProdutoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void update(Long id, String nomeImagem) {

        Produto entity = produtoRepository.findById(id);

        if (entity == null)
            throw new NullPointerException("Nenhum café encontrado");

        entity.setNomeImagem(nomeImagem);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Produto produto = produtoRepository.findById(id);
        
        avaliacaoService.delete(produto);

        usuarioService.deleteProdutoFromListaDesejo(produto);

        if (produtoRepository.isPersistent(produto))
            produtoRepository.delete(produto);

        else
            throw new NotFoundException("Nenhum ração encontrado");
    }

    @Override
    public Long count() {

        return produtoRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return produtoRepository.findByNome(nome).count();
    }

    @Override
    public List<ProdutoResponseDTO> getByNome(String nome, int page, int pageSize) throws NullPointerException {

        List<Produto> list = produtoRepository.findByNome(nome).page(page, pageSize).list();

        if (list == null)
            throw new NullPointerException("nenhum ração encontrado");

        return list.stream().map(e -> ProdutoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    // @Override
    // public List<ProdutoResponseDTO> getByMarca(String nome) throws NullPointerException {

    //     List<Produto> list = produtoRepository.findByMarca(marcaRepository.findByNome(nome).get(0));

    //     if (list == null)
    //         throw new NullPointerException("Nenhuma marca encontrada");

    //     return list.stream()
    //             .map(ProdutoResponseDTO::new)
    //             .collect(Collectors.toList());
    // }

    // @Override
    // public List<ProdutoResponseDTO> filterByPrecoMin(Double preco) throws NullPointerException {

    //     List<Produto> list = produtoRepository.filterByPrecoMinimo(preco);

    //     if (list == null)
    //         throw new NullPointerException("Nenhum ração encontrada");

    //     return list.stream()
    //             .map(ProdutoResponseDTO::new)
    //             .collect(Collectors.toList());
    // }

    // @Override
    // public List<ProdutoResponseDTO> filterByPrecoMax(Double preco) {

    //     List<Produto> list = produtoRepository.filterByPrecoMaximo(preco);

    //     if (list == null)
    //         throw new NullPointerException("Nenhum ração encontrada");

    //     return list.stream()
    //             .map(ProdutoResponseDTO::new)
    //             .collect(Collectors.toList());
    // }

    // @Override
    // public List<ProdutoResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax) {

    //     List<Produto> list = produtoRepository.filterByEntrePreco(precoMin, precoMax);

    //     if (list == null)
    //         throw new NullPointerException("Nenhum ração encontrada");

    //     return list.stream()
    //             .map(ProdutoResponseDTO::new)
    //             .collect(Collectors.toList());
    // }

    private void validar(ProdutoDTO produtoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

}
