package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.FornecedorDTO;
import br.unitins.ecommerce.dto.produto.FornecedorResponseDTO;
import jakarta.validation.Valid;

public interface FornecedorService {
    
    // Metodos basicos

    List<FornecedorResponseDTO> getAll(int page, int pageSize);
    
    FornecedorResponseDTO getById(Long id);

    FornecedorResponseDTO insert(@Valid FornecedorDTO fornecedorDto);

    FornecedorResponseDTO update(Long id, @Valid FornecedorDTO fornecedorDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    Long countByNome(String nome);

    List<FornecedorResponseDTO> getByNome(String nome, int page , int pageSize);
}
