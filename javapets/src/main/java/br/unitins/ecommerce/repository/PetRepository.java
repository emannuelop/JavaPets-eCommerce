package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.usuario.Pet;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PetRepository implements PanacheRepository<Pet> {
    
    public PanacheQuery<Pet> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Pet WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }
}
