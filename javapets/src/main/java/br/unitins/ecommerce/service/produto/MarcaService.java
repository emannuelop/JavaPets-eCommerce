package br.unitins.ecommerce.service.produto;

import java.util.List;

import br.unitins.ecommerce.dto.produto.MarcaDTO;
import br.unitins.ecommerce.dto.produto.MarcaResponseDTO;

public interface MarcaService {
    
    // Metodos basicos

    List<MarcaResponseDTO> getAll();
    
    MarcaResponseDTO getById(Long id);

    MarcaResponseDTO insert(MarcaDTO marcaDto);

    MarcaResponseDTO update(Long id, MarcaDTO marcaDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<MarcaResponseDTO> getByNome(String nome);
}
