package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.produto.produto.Marca;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {
    
    public PanacheQuery findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Marca WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }
}
