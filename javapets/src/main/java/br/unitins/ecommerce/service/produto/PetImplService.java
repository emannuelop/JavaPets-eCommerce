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

import br.unitins.ecommerce.dto.produto.PetDTO;
import br.unitins.ecommerce.dto.produto.PetResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Pet;
import br.unitins.ecommerce.repository.EstadoRepository;
import br.unitins.ecommerce.repository.PetRepository;

@ApplicationScoped
public class PetImplService implements PetService {

    @Inject
    Validator validator;

    @Inject
    PetRepository petRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    public List<PetResponseDTO> getAll() {
        
        return petRepository.findAll()
                                    .stream()
                                    .map(PetResponseDTO::new)
                                    .toList();
    }

    @Override
    public PetResponseDTO getById(Long id) throws NotFoundException {
        
        Pet pet = petRepository.findById(id);

        if (pet == null)
            throw new NotFoundException("Não encontrado");

        return new PetResponseDTO(pet);
    }

    @Override
    @Transactional
    public PetResponseDTO insert(PetDTO petDto) throws ConstraintViolationException {
        
        validar(petDto);

        Pet entity = new Pet();

        entity.setNome(petDto.nome());

        entity.setEspecie(petDto.especie());

        entity.setRaca(petDto.raca());

        entity.setIdadeEmMeses(petDto.idadeEmMeses());

        petRepository.persist(entity);

        return new PetResponseDTO(entity);
    }

    @Override
    @Transactional
    public PetResponseDTO update(Long id, PetDTO petDto) throws ConstraintViolationException, NotFoundException {
        
        validar(petDto);

        Pet entity = petRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setNome(petDto.nome());

        entity.setEspecie(petDto.especie());

        entity.setRaca(petDto.raca());

        entity.setIdadeEmMeses(petDto.idadeEmMeses());

        return new PetResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Pet pet = petRepository.findById(id);

        if (petRepository.isPersistent(pet))
            petRepository.delete(pet);

        else
            throw new NotFoundException("Nenhum pet encontrado");
    }

    @Override
    public Long count() {

        return petRepository.count();
    }

    @Override
    public List<PetResponseDTO> getByNome(String nome) throws NullPointerException {
        
        List<Pet> list = petRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum pet encontrado");

        return list.stream()
                    .map(PetResponseDTO::new)
                    .collect(Collectors.toList());
    }
    
    private void validar(PetDTO petDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<PetDTO>> violations = validator.validate(petDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
