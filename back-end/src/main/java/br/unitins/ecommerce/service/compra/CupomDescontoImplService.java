package br.unitins.ecommerce.service.compra;

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

import br.unitins.ecommerce.dto.compra.CupomDescontoDTO;
import br.unitins.ecommerce.dto.compra.CupomDescontoResponseDTO;
import br.unitins.ecommerce.model.compra.CupomDesconto;
import br.unitins.ecommerce.repository.CupomDescontoRepository;
import br.unitins.ecommerce.validation.ValidationException;

@ApplicationScoped
public class CupomDescontoImplService implements CupomDescontoService {

    @Inject
    CupomDescontoRepository cupomDescontoRepository;

    @Inject
    Validator validator;

    @Override
    public List<CupomDescontoResponseDTO> getAll(int page, int pageSize) {

        return cupomDescontoRepository.findAll().page(page, pageSize).list()
                                .stream()
                                .map(CupomDescontoResponseDTO::new)
                                .collect(Collectors.toList());
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
    public CupomDescontoResponseDTO insert(@Valid CupomDescontoDTO cupomDescontoDto) throws ConstraintViolationException {
        
        if(cupomDescontoRepository.findByNomeValidation(cupomDescontoDto.codigoCupom()) != null){
            throw new ValidationException("nome","O cupom desconto "+cupomDescontoDto.codigoCupom()+" já está cadastrada.");
        }

        CupomDesconto entity = new CupomDesconto();

        entity.setCodigoCupom(cupomDescontoDto.codigoCupom());

        entity.setQuantidadeDisponivel(cupomDescontoDto.quantidadeDisponivel());

        entity.setPorcentagemDesconto(cupomDescontoDto.porcentagemDesconto());

        cupomDescontoRepository.persist(entity);

        return new CupomDescontoResponseDTO(entity);
    }

    @Override
    @Transactional
    public CupomDescontoResponseDTO update(Long id, @Valid CupomDescontoDTO cupomDescontoDto) throws ConstraintViolationException, NotFoundException {
        
        //validar(cupomDescontoDto);

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
    public List<CupomDescontoResponseDTO> getByNome(String nome, int page, int pageSize) throws NullPointerException {
        
        List<CupomDesconto> list = cupomDescontoRepository.findByNome(nome).page(page, pageSize).list();

        if (list == null)
            throw new NullPointerException("nenhum cupomDesconto encontrado");

        return list.stream()
                    .map(CupomDescontoResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public Long count() {

        return cupomDescontoRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return cupomDescontoRepository.findByNome(nome).count();
    }
    
    private void validar(CupomDescontoDTO cupomDescontoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<CupomDescontoDTO>> violations = validator.validate(cupomDescontoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
