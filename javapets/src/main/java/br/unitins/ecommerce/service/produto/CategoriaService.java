package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.CategoriaDTO;
import br.unitins.ecommerce.dto.produto.CategoriaResponseDTO;

public interface CategoriaService {
    
    // Metodos basicos

    List<CategoriaResponseDTO> getAll();
    
    CategoriaResponseDTO getById(Long id);

    CategoriaResponseDTO insert(CategoriaDTO categoriaDto);

    CategoriaResponseDTO update(Long id, CategoriaDTO categoriaDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<CategoriaResponseDTO> getByNome(String nome);
}
