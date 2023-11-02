package br.unitins.ecommerce.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDTO(
        @NotBlank(message = "Campo nome não pode estar vazio") String nome,

        @NotBlank(message = "Campo descricao não pode estar vazio")
         @Size(min = 10, max = 60, message = "O campo descrição deve possuir no mínimo 10 caracteres")
          String descricao) {

}
