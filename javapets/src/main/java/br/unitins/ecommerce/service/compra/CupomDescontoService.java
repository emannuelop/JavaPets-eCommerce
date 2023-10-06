package br.unitins.ecommerce.service.compra;

import java.util.List;

import br.unitins.ecommerce.dto.compra.CupomDescontoDTO;
import br.unitins.ecommerce.dto.compra.CupomDescontoResponseDTO;

public interface CupomDescontoService {
    
    // Metodos basicos

    List<CupomDescontoResponseDTO> getAll(int page, int pageSize);
    
    CupomDescontoResponseDTO getById(Long id);

    CupomDescontoResponseDTO insert(CupomDescontoDTO cupomDescontoDto);

    CupomDescontoResponseDTO update(Long id, CupomDescontoDTO cupomDescontoDto);

    void delete(Long id);

    // Metodos extras

    List<CupomDescontoResponseDTO> getByNome(String nome, int page, int pageSize);

    Long count();

    Long countByNome(String nome);
}
