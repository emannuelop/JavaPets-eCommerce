package br.unitins.ecommerce.service.usuario;

import java.util.List;

import br.unitins.ecommerce.dto.usuario.PetDTO;
import br.unitins.ecommerce.dto.usuario.PetResponseDTO;
import jakarta.validation.Valid;

public interface PetService {
    
    // Metodos basicos

    List<PetResponseDTO> getAll(int page, int pageSize);
    
    PetResponseDTO getById(Long id);

    PetResponseDTO insert(@Valid PetDTO petDto);

    PetResponseDTO update(Long id, @Valid PetDTO petDto);

    void delete(Long id);

    // Metodos extras

    List<PetResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);
}
