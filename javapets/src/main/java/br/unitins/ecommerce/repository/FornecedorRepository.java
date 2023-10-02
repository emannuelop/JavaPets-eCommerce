package br.unitins.ecommerce.repository;


import br.unitins.ecommerce.model.produto.produto.Fornecedor;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {
    
    public PanacheQuery findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Fornecedor WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }
}
