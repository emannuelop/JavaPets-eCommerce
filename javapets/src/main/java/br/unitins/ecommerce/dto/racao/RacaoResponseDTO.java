package br.unitins.ecommerce.dto.racao;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.ecommerce.model.produto.racao.EscolhaAnimal;
import br.unitins.ecommerce.model.produto.racao.Racao;

public record RacaoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String nomeImagem,
    Double preco,
    String estoque,
    String nomeMarca,
    Double quantidadeQuilos,
    String sabor,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    EscolhaAnimal escolhaAnimal
) {
    
    public RacaoResponseDTO(Racao racao) {

        this(racao.getId(),
            racao.getNome(),
            racao.getDescricao(),
            racao.getNomeImagem(),
            racao.getPreco(),
            racao.getEstoque() > 0? "Disponível" : "Estoque esgotado",
            racao.getMarca().getNome(),
            racao.getQuantidadeQuilos(),
            racao.getSabor(),
            racao.getEscolhaAnimal());
    }
}
