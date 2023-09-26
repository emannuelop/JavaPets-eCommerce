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
import br.unitins.ecommerce.repository.MarcaRepository;
import br.unitins.ecommerce.repository.ProdutoRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@ApplicationScoped
public class ProdutoImplService implements ProdutoService {

    @Inject
    ProdutoRepository racaoRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    @Override
    public List<ProdutoResponseDTO> getAll() {

        return racaoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    @Override
    public ProdutoResponseDTO getById(Long id) throws NotFoundException {

        Produto racao = racaoRepository.findById(id);

        if (racao == null)
            throw new NotFoundException("Não encontrado");

        return new ProdutoResponseDTO(racao);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO insert(ProdutoDTO racaoDto) throws ConstraintViolationException {

        validar(racaoDto);

        Produto entity = new Produto();

        entity.setNome(racaoDto.nome());

        entity.setDescricao(racaoDto.descricao());

        entity.setMarca(marcaRepository.findById(racaoDto.idMarca()));

        entity.setPreco(racaoDto.preco());

        entity.setEstoque(racaoDto.estoque());

        racaoRepository.persist(entity);

        return new ProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(Long id, ProdutoDTO racaoDto) throws ConstraintViolationException {

        validar(racaoDto);

        Produto entity = racaoRepository.findById(id);

        entity.setNome(racaoDto.nome());

        entity.setDescricao(racaoDto.descricao());

        entity.setMarca(marcaRepository.findById(racaoDto.idMarca()));

        entity.setPreco(racaoDto.preco());

        entity.setEstoque(racaoDto.estoque());

        return new ProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void update(Long id, String nomeImagem) {

        Produto entity = racaoRepository.findById(id);

        if (entity == null)
            throw new NullPointerException("Nenhum café encontrado");

        entity.setNomeImagem(nomeImagem);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Produto racao = racaoRepository.findById(id);
        
        avaliacaoService.delete(racao);

        usuarioService.deleteProdutoFromListaDesejo(racao);

        if (racaoRepository.isPersistent(racao))
            racaoRepository.delete(racao);

        else
            throw new NotFoundException("Nenhum ração encontrado");
    }

    @Override
    public Long count() {

        return racaoRepository.count();
    }

    @Override
    public List<ProdutoResponseDTO> getByNome(String nome) throws NullPointerException {

        List<Produto> list = racaoRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum ração encontrado");

        return list.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponseDTO> getByMarca(String nome) throws NullPointerException {

        List<Produto> list = racaoRepository.findByMarca(marcaRepository.findByNome(nome).get(0));

        if (list == null)
            throw new NullPointerException("Nenhuma marca encontrada");

        return list.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponseDTO> filterByPrecoMin(Double preco) throws NullPointerException {

        List<Produto> list = racaoRepository.filterByPrecoMinimo(preco);

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponseDTO> filterByPrecoMax(Double preco) {

        List<Produto> list = racaoRepository.filterByPrecoMaximo(preco);

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax) {

        List<Produto> list = racaoRepository.filterByEntrePreco(precoMin, precoMax);

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void validar(ProdutoDTO racaoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(racaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

}
