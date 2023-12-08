package br.unitins.ecommerce.dto.usuario;

import br.unitins.ecommerce.model.usuario.Pet;

public record PetResponseDTO (
    Long id,
    String nome,
    String especie,
    String raca,
    Integer idadeEmMeses
) {

    public PetResponseDTO(Pet pet) {
        
        this(pet.getId(),
            pet.getNome(),
            pet.getEspecie(),
            pet.getRaca(),
            pet.getIdadeEmMeses());
    }

}
