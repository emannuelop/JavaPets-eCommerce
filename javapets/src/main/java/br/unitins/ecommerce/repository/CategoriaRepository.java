package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.produto.produto.Categoria;
import br.unitins.ecommerce.model.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {
    
    public PanacheQuery findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Categoria WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
        
    }

    public Categoria findByNomeValidation(String nome) {

        if (nome == null)
            return null;

        return find("nome = ?1 ", nome).firstResult();
    }
}
