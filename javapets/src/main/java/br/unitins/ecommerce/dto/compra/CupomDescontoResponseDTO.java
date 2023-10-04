package br.unitins.ecommerce.dto.compra;

import br.unitins.ecommerce.model.compra.CupomDesconto;

public record CupomDescontoResponseDTO (
    Long id,
    String codigoCupom,
    Integer quantidadeDisponivel,
    Integer porcentagemDesconto
) {

    public CupomDescontoResponseDTO(CupomDesconto cupomDesconto) {
        
        this(cupomDesconto.getId(),
            cupomDesconto.getCodigoCupom(),
            cupomDesconto.getQuantidadeDisponivel(),
            cupomDesconto.getPorcentagemDesconto());
    }

}
