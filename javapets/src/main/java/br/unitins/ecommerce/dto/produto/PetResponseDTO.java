package br.unitins.ecommerce.dto.produto;

import br.unitins.ecommerce.model.produto.produto.Pet;


public record PetResponseDTO (
    Long id,
    String nome,
    String especie,
    String raca,
    int idadeEmMeses
) {

    public PetResponseDTO(Pet pet) {
        
        this(pet.getId(),
            pet.getNome(),
            pet.getEspecie()
            ,pet.getRaca(),
            pet.getIdadeEmMeses());
    }

}
