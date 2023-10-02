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

import br.unitins.ecommerce.dto.produto.MarcaDTO;
import br.unitins.ecommerce.dto.produto.MarcaResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Marca;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.repository.MarcaRepository;

@ApplicationScoped
public class MarcaImplService implements MarcaService {

    @Inject
    Validator validator;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    public List<MarcaResponseDTO> getAll(int page, int pageSize) {

        return marcaRepository.findAll().page(page, pageSize).list()
                                    .stream()
                                    .map(MarcaResponseDTO::new)
                                    .toList();
    }

    @Override
    public MarcaResponseDTO getById(Long id) throws NotFoundException {
        
        Marca marca = marcaRepository.findById(id);

        if (marca == null)
            throw new NotFoundException("Não encontrado");

        return new MarcaResponseDTO(marca);
    }

    @Override
    @Transactional
    public MarcaResponseDTO insert(MarcaDTO marcaDto) throws ConstraintViolationException {
        
        validar(marcaDto);

        Marca entity = new Marca();

        entity.setNome(marcaDto.nome());

        entity.setCnpj(marcaDto.cnpj());

        marcaRepository.persist(entity);

        return new MarcaResponseDTO(entity);
    }

    @Override
    @Transactional
    public MarcaResponseDTO update(Long id, MarcaDTO marcaDto) throws ConstraintViolationException, NotFoundException {
        
        validar(marcaDto);

        Marca entity = marcaRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setNome(marcaDto.nome());

        entity.setCnpj(marcaDto.cnpj());

        return new MarcaResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Marca marca = marcaRepository.findById(id);

        if (marcaRepository.isPersistent(marca))
            marcaRepository.delete(marca);

        else
            throw new NotFoundException("Nenhum marca encontrado");
    }

    @Override
    public Long count() {

        return marcaRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return marcaRepository.findByNome(nome).count();
    }

    @Override
    public List<MarcaResponseDTO> getByNome(String nome, int page, int pageSize) throws NullPointerException {
        
        List<Marca> list = marcaRepository.findByNome(nome).page(page, pageSize).list();

        if (list == null)
            throw new NullPointerException("nenhum marca encontrado");

        return list.stream()
                    .map(MarcaResponseDTO::new)
                    .collect(Collectors.toList());
    }
    
    private void validar(MarcaDTO marcaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<MarcaDTO>> violations = validator.validate(marcaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
