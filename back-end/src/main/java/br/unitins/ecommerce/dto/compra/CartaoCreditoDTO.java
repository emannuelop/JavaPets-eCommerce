package br.unitins.ecommerce.dto.compra;

import java.time.LocalDate;

public record CartaoCreditoDTO(
    String numeroCartao,
    String nomeImpressoCartao,
    String codigoSeguranca,
    Integer bandeiraCartao
) {
    
}
