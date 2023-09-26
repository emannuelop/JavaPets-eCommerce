package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.PetDTO;
import br.unitins.ecommerce.dto.produto.PetResponseDTO;

public interface PetService {
    
    // Metodos basicos

    List<PetResponseDTO> getAll();
    
    PetResponseDTO getById(Long id);

    PetResponseDTO insert(PetDTO petDto);

    PetResponseDTO update(Long id, PetDTO petDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<PetResponseDTO> getByNome(String nome);
}
