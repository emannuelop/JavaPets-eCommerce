package br.unitins.ecommerce.service.estado;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.model.endereco.Estado;
import br.unitins.ecommerce.repository.EstadoRepository;

@ApplicationScoped
public class EstadoImplService implements EstadoService {

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    Validator validator;

    @Override
    public List<EstadoResponseDTO> getAll(int page, int pageSize) {

        //estadoRepository.findAll().page(page, pageSize).list();

        return estadoRepository.findAll().page(page, pageSize).list()
                                .stream()
                                .map(EstadoResponseDTO::new)
                                .collect(Collectors.toList());
    }

    @Override
    public EstadoResponseDTO getById(Long id) throws NotFoundException {

        Estado estado = estadoRepository.findById(id);

        if (estado == null)
            throw new NotFoundException("Não encontrado");

        return new EstadoResponseDTO(estado);
    }

    @Override
    @Transactional
    public EstadoResponseDTO insert(@Valid EstadoDTO estadoDto) throws ConstraintViolationException {

        validar(estadoDto);

        Estado entity = new Estado();

        entity.setNome(estadoDto.nome());

        entity.setSigla(estadoDto.sigla().toUpperCase());

        estadoRepository.persist(entity);

        return new EstadoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update( Long id, @Valid  EstadoDTO estadoDto) throws ConstraintViolationException {

        // validar(estadoDto);

        Estado entity = estadoRepository.findById(id);

        entity.setNome(estadoDto.nome());

        entity.setSigla(estadoDto.sigla().toUpperCase());

        return new EstadoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Estado estado = estadoRepository.findById(id);

        if (estadoRepository.isPersistent(estado))
            estadoRepository.delete(estado);

        else
            throw new NotFoundException("Nenhum estado encontrado");
    }

    @Override
    public List<EstadoResponseDTO> getByNome(String nome, int page, int pageSize) throws NullPointerException {

        List<Estado> list = estadoRepository.findByNome(nome).page(page, pageSize).list();

        if (list == null)
            throw new NullPointerException("nenhum Estado encontrado");

        return list.stream()
                    .map(EstadoResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<EstadoResponseDTO> getBySigla(String sigla) throws NullPointerException {
        
        List<Estado> list = estadoRepository.findBySigla(sigla);

        if (list == null)
            throw new NullPointerException("nenhum Estado encontrado");

        return list.stream()
                    .map(EstadoResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public Long count() {

        return estadoRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return estadoRepository.findByNome(nome).count();
    }

    private void validar(EstadoDTO estadoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<EstadoDTO>> violations = validator.validate(estadoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
