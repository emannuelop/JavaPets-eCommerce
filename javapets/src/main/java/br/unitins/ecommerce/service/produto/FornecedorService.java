package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.FornecedorDTO;
import br.unitins.ecommerce.dto.produto.FornecedorResponseDTO;

public interface FornecedorService {
    
    // Metodos basicos

    List<FornecedorResponseDTO> getAll();
    
    FornecedorResponseDTO getById(Long id);

    FornecedorResponseDTO insert(FornecedorDTO fornecedorDto);

    FornecedorResponseDTO update(Long id, FornecedorDTO fornecedorDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<FornecedorResponseDTO> getByNome(String nome);
}
