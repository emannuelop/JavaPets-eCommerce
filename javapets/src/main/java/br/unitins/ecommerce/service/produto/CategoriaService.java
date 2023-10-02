package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.CategoriaDTO;
import br.unitins.ecommerce.dto.produto.CategoriaResponseDTO;

public interface CategoriaService {
    
    // Metodos basicos

    List<CategoriaResponseDTO> getAll(int page, int pageSize);
    
    CategoriaResponseDTO getById(Long id);

    CategoriaResponseDTO insert(CategoriaDTO categoriaDto);

    CategoriaResponseDTO update(Long id, CategoriaDTO categoriaDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    Long countByNome(String nome);

    List<CategoriaResponseDTO> getByNome(String nome, int page, int pageSize);
}
