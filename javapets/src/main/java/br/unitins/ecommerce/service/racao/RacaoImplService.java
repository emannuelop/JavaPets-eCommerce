package br.unitins.ecommerce.service.racao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.racao.RacaoDTO;
import br.unitins.ecommerce.dto.racao.RacaoResponseDTO;
import br.unitins.ecommerce.model.produto.racao.EscolhaAnimal;
import br.unitins.ecommerce.model.produto.racao.Racao;
import br.unitins.ecommerce.repository.MarcaRepository;
import br.unitins.ecommerce.repository.RacaoRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@ApplicationScoped
public class RacaoImplService implements RacaoService {

    @Inject
    RacaoRepository racaoRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    @Override
    public List<RacaoResponseDTO> getAll() {

        return racaoRepository.findAll()
                .stream()
                .map(RacaoResponseDTO::new)
                .toList();
    }

    @Override
    public RacaoResponseDTO getById(Long id) throws NotFoundException {

        Racao racao = racaoRepository.findById(id);

        if (racao == null)
            throw new NotFoundException("Não encontrado");

        return new RacaoResponseDTO(racao);
    }

    @Override
    @Transactional
    public RacaoResponseDTO insert(RacaoDTO racaoDto) throws ConstraintViolationException {

        validar(racaoDto);

        Racao entity = new Racao();

        entity.setNome(racaoDto.nome());

        entity.setDescricao(racaoDto.descricao());

        entity.setMarca(marcaRepository.findById(racaoDto.idMarca()));

        entity.setPreco(racaoDto.preco());

        entity.setEstoque(racaoDto.estoque());

        entity.setQuantidadeQuilos(racaoDto.quantidadeQuilos());

        entity.setSabor(racaoDto.sabor());

        entity.setEscolhaAnimal(EscolhaAnimal.valueOf(racaoDto.escolhaAnimal()));

        racaoRepository.persist(entity);

        return new RacaoResponseDTO(entity);
    }

    @Override
    @Transactional
    public RacaoResponseDTO update(Long id, RacaoDTO racaoDto) throws ConstraintViolationException {

        validar(racaoDto);

        Racao entity = racaoRepository.findById(id);

        entity.setNome(racaoDto.nome());

        entity.setDescricao(racaoDto.descricao());

        entity.setMarca(marcaRepository.findById(racaoDto.idMarca()));

        entity.setPreco(racaoDto.preco());

        entity.setEstoque(racaoDto.estoque());

        entity.setQuantidadeQuilos(racaoDto.quantidadeQuilos());

        entity.setSabor(racaoDto.sabor());

        entity.setEscolhaAnimal(EscolhaAnimal.valueOf(racaoDto.escolhaAnimal()));

        return new RacaoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Racao racao = racaoRepository.findById(id);
        
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
    public List<RacaoResponseDTO> getByNome(String nome) throws NullPointerException {

        List<Racao> list = racaoRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum ração encontrado");

        return list.stream()
                .map(RacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RacaoResponseDTO> getByEscolhaAnimal(Integer id)
            throws IndexOutOfBoundsException, NullPointerException {

        if (id < 1 || id > 2)
            throw new IndexOutOfBoundsException("número fora das opções");

        List<Racao> list = racaoRepository.findByEscolhaAnimal(EscolhaAnimal.valueOf(id));

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(RacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RacaoResponseDTO> getByMarca(String nome) throws NullPointerException {

        List<Racao> list = racaoRepository.findByMarca(marcaRepository.findByNome(nome).get(0));

        if (list == null)
            throw new NullPointerException("Nenhuma marca encontrada");

        return list.stream()
                .map(RacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RacaoResponseDTO> filterByPrecoMin(Double preco) throws NullPointerException {

        List<Racao> list = racaoRepository.filterByPrecoMinimo(preco);

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(RacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RacaoResponseDTO> filterByPrecoMax(Double preco) {

        List<Racao> list = racaoRepository.filterByPrecoMaximo(preco);

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(RacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<RacaoResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax) {

        List<Racao> list = racaoRepository.filterByEntrePreco(precoMin, precoMax);

        if (list == null)
            throw new NullPointerException("Nenhum ração encontrada");

        return list.stream()
                .map(RacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void validar(RacaoDTO racaoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<RacaoDTO>> violations = validator.validate(racaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

}
