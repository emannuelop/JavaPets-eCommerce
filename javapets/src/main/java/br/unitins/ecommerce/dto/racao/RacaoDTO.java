package br.unitins.ecommerce.dto.racao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RacaoDTO(
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotBlank(message = "Campo descricao não pode estar vazio")
    String descricao,

    @NotNull(message = "Campo idmarca não pode estar vazio")
    @Min(1)
    Long idMarca,

    @NotNull(message = "Campo preço não pode estar vazio")
    @Min(1)
    Double preco,

    @NotNull(message = "Campo estoque não pode estar vazio")
    @Min(0)
    Integer estoque,

    @NotNull(message = "Campo quantidade de quilos não pode estar vazio")
    Double quantidadeQuilos,

    @NotBlank(message = "Campo sabor não pode estar vazio")
    String sabor,

    @NotNull(message = "Campo escolhaAnimal não pode estar vazio")
    @Min(1)
    @Max(2)
    Integer escolhaAnimal
) {
    
}
