package br.unitins.ecommerce.repository;

import java.util.List;

import br.unitins.ecommerce.model.produto.produto.Categoria;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {
    
    public List<Categoria> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Categoria WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
