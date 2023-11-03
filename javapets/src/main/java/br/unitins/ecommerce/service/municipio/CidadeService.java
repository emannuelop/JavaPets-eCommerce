package br.unitins.ecommerce.service.municipio;

import java.util.List;

import br.unitins.ecommerce.dto.municipio.CidadeDTO;
import br.unitins.ecommerce.dto.municipio.CidadeResponseDTO;
import jakarta.validation.Valid;


public interface CidadeService {

        // recursos basicos
        List<CidadeResponseDTO> getAll(int page, int pageSize);

        CidadeResponseDTO findById(Long id);
    
        CidadeResponseDTO create(@Valid CidadeDTO dto);
    
        CidadeResponseDTO update(Long id, @Valid CidadeDTO dto);
    
        void delete(Long id);
    
        // recursos extras
    
        List<CidadeResponseDTO> findByNome(String nome, int page, int pageSize);
    
        long count();

        long countByNome(String nome);
    
}