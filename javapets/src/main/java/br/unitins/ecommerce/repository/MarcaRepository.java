package br.unitins.ecommerce.repository;

import java.util.List;

import br.unitins.ecommerce.model.produto.produto.Marca;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {
    
    public List<Marca> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Marca WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
