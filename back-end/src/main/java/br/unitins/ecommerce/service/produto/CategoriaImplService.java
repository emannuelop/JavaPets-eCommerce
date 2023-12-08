package br.unitins.ecommerce.service.produto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.ecommerce.dto.produto.CategoriaDTO;
import br.unitins.ecommerce.dto.produto.CategoriaResponseDTO;
import br.unitins.ecommerce.model.produto.produto.Categoria;
import br.unitins.ecommerce.repository.CategoriaRepository;
import br.unitins.ecommerce.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaImplService implements CategoriaService {

    @Inject
    Validator validator;

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaResponseDTO> getAll(int page, int pageSize) {
        
        return categoriaRepository.findAll().page(page, pageSize).list()
                                    .stream()
                                    .map(CategoriaResponseDTO::new)
                                    .toList();
    }

    @Override
    public CategoriaResponseDTO getById(Long id) throws NotFoundException {
        
        Categoria categoria = categoriaRepository.findById(id);

        if (categoria == null)
            throw new NotFoundException("Não encontrado");

        return new CategoriaResponseDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO insert(@Valid CategoriaDTO categoriaDto) throws ConstraintViolationException {
        if(categoriaRepository.findByNomeValidation(categoriaDto.nome()) != null){
            throw new ValidationException("nome","A categoria "+categoriaDto.nome()+" já está cadastrada.");
        }
       // validar(categoriaDto);

        Categoria entity = new Categoria();

        entity.setNome(categoriaDto.nome());

        entity.setDescricao(categoriaDto.descricao());

        categoriaRepository.persist(entity);

        return new CategoriaResponseDTO(entity);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, @Valid CategoriaDTO categoriaDto) throws ConstraintViolationException, NotFoundException {
        
        //validar(categoriaDto);

        Categoria entity = categoriaRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setNome(categoriaDto.nome());

        entity.setDescricao(categoriaDto.descricao());

        return new CategoriaResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Categoria categoria = categoriaRepository.findById(id);

        if (categoriaRepository.isPersistent(categoria))
            categoriaRepository.delete(categoria);

        else
            throw new NotFoundException("Nenhum categoria encontrado");
    }

    @Override
    public Long count() {

        return categoriaRepository.count();
    }

    @Override
    public Long countByNome(String nome) {

        return categoriaRepository.findByNome(nome).count();
    }

    @Override
    public List<CategoriaResponseDTO> getByNome(String nome, int page, int pageSize) throws NullPointerException {
        
        List<Categoria> list = categoriaRepository.findByNome(nome).page(page, pageSize).list();

        if (list == null)
            throw new NullPointerException("nenhum categoria encontrado");

        return list.stream()
                    .map(CategoriaResponseDTO::new)
                    .collect(Collectors.toList());
    }
    
    private void validar(CategoriaDTO categoriaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<CategoriaDTO>> violations = validator.validate(categoriaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
