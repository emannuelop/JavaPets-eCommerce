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

import br.unitins.ecommerce.dto.produto.FornecedorDTO;
import br.unitins.ecommerce.dto.produto.FornecedorResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Fornecedor;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.repository.FornecedorRepository;

@ApplicationScoped
public class FornecedorImplService implements FornecedorService {

    @Inject
    Validator validator;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    public List<FornecedorResponseDTO> getAll() {
        
        return fornecedorRepository.findAll()
                                    .stream()
                                    .map(FornecedorResponseDTO::new)
                                    .toList();
    }

    @Override
    public FornecedorResponseDTO getById(Long id) throws NotFoundException {
        
        Fornecedor fornecedor = fornecedorRepository.findById(id);

        if (fornecedor == null)
            throw new NotFoundException("Não encontrado");

        return new FornecedorResponseDTO(fornecedor);
    }

    @Override
    @Transactional
    public FornecedorResponseDTO insert(FornecedorDTO fornecedorDto) throws ConstraintViolationException {
        
        validar(fornecedorDto);

        Fornecedor entity = new Fornecedor();

        entity.setNome(fornecedorDto.nome());

        fornecedorRepository.persist(entity);

        return new FornecedorResponseDTO(entity);
    }

    @Override
    @Transactional
    public FornecedorResponseDTO update(Long id, FornecedorDTO fornecedorDto) throws ConstraintViolationException, NotFoundException {
        
        validar(fornecedorDto);

        Fornecedor entity = fornecedorRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setNome(fornecedorDto.nome());

        return new FornecedorResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Fornecedor fornecedor = fornecedorRepository.findById(id);

        if (fornecedorRepository.isPersistent(fornecedor))
            fornecedorRepository.delete(fornecedor);

        else
            throw new NotFoundException("Nenhum fornecedor encontrado");
    }

    @Override
    public Long count() {

        return fornecedorRepository.count();
    }

    @Override
    public List<FornecedorResponseDTO> getByNome(String nome) throws NullPointerException {
        
        List<Fornecedor> list = fornecedorRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum fornecedor encontrado");

        return list.stream()
                    .map(FornecedorResponseDTO::new)
                    .collect(Collectors.toList());
    }
    
    private void validar(FornecedorDTO fornecedorDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FornecedorDTO>> violations = validator.validate(fornecedorDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
