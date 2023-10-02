package br.unitins.ecommerce.service.compra;

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

import br.unitins.ecommerce.dto.compra.CupomDescontoDTO;
import br.unitins.ecommerce.dto.compra.CupomDescontoResponseDTO;
import br.unitins.ecommerce.model.compra.CupomDesconto;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.repository.CupomDescontoRepository;

@ApplicationScoped
public class CupomDescontoImplService implements CupomDescontoService {

    @Inject
    Validator validator;

    @Inject
    CupomDescontoRepository cupomDescontoRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    public List<CupomDescontoResponseDTO> getAll() {
        
        return cupomDescontoRepository.findAll()
                                    .stream()
                                    .map(CupomDescontoResponseDTO::new)
                                    .toList();
    }

    @Override
    public CupomDescontoResponseDTO getById(Long id) throws NotFoundException {
        
        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id);

        if (cupomDesconto == null)
            throw new NotFoundException("Não encontrado");

        return new CupomDescontoResponseDTO(cupomDesconto);
    }

    @Override
    @Transactional
    public CupomDescontoResponseDTO insert(CupomDescontoDTO cupomDescontoDto) throws ConstraintViolationException {
        
        validar(cupomDescontoDto);

        CupomDesconto entity = new CupomDesconto();

        entity.setCodigoCupom(cupomDescontoDto.codigoCupom());

        entity.setQuantidadeDisponivel(cupomDescontoDto.quantidadeDisponivel());

        entity.setPorcentagemDesconto(cupomDescontoDto.porcentagemDesconto());

        cupomDescontoRepository.persist(entity);

        return new CupomDescontoResponseDTO(entity);
    }

    @Override
    @Transactional
    public CupomDescontoResponseDTO update(Long id, CupomDescontoDTO cupomDescontoDto) throws ConstraintViolationException, NotFoundException {
        
        validar(cupomDescontoDto);

        CupomDesconto entity = cupomDescontoRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setCodigoCupom(cupomDescontoDto.codigoCupom());

        entity.setQuantidadeDisponivel(cupomDescontoDto.quantidadeDisponivel());

        entity.setPorcentagemDesconto(cupomDescontoDto.porcentagemDesconto());

        return new CupomDescontoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id);

        if (cupomDescontoRepository.isPersistent(cupomDesconto))
            cupomDescontoRepository.delete(cupomDesconto);

        else
            throw new NotFoundException("Nenhum cupomDesconto encontrado");
    }

    @Override
    public Long count() {

        return cupomDescontoRepository.count();
    }

    @Override
    public List<CupomDescontoResponseDTO> getByNome(String nome) throws NullPointerException {
        
        List<CupomDesconto> list = cupomDescontoRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum cupomDesconto encontrado");

        return list.stream()
                    .map(CupomDescontoResponseDTO::new)
                    .collect(Collectors.toList());
    }
    
    private void validar(CupomDescontoDTO cupomDescontoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<CupomDescontoDTO>> violations = validator.validate(cupomDescontoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
