package br.unitins.ecommerce.repository;

import java.util.List;

import br.unitins.ecommerce.model.produto.produto.Pet;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PetRepository implements PanacheRepository<Pet> {
    
    public List<Pet> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Pet WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
