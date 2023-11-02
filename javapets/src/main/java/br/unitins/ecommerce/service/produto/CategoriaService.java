package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.CategoriaDTO;
import br.unitins.ecommerce.dto.produto.CategoriaResponseDTO;
import jakarta.validation.Valid;

public interface CategoriaService {
    
    // Metodos basicos

    List<CategoriaResponseDTO> getAll(int page, int pageSize);
    
    CategoriaResponseDTO getById(Long id);

    CategoriaResponseDTO insert(@Valid CategoriaDTO categoriaDto);

    CategoriaResponseDTO update(Long id, @Valid CategoriaDTO categoriaDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    Long countByNome(String nome);

    List<CategoriaResponseDTO> getByNome(String nome, int page, int pageSize);
}
