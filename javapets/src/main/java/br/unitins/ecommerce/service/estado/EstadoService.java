package br.unitins.ecommerce.service.estado;

import java.util.List;

import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import jakarta.validation.Valid;

public interface EstadoService {
    
    List<EstadoResponseDTO> getAll(int page, int pageSize);
    
    EstadoResponseDTO getById(Long id);

    EstadoResponseDTO insert(@Valid EstadoDTO estadoDto);

    EstadoResponseDTO update(@Valid Long id, EstadoDTO estadoDto);

    void delete(Long id);

    // Metodos extras

    List<EstadoResponseDTO> getByNome(String nome, int page, int pageSize);

    List<EstadoResponseDTO> getBySigla(String sigla);

    Long count();

    Long countByNome(String nome);
}
