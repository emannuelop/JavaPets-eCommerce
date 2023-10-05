package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.compra.CupomDesconto;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CupomDescontoRepository implements PanacheRepository<CupomDesconto> {
    
    public PanacheQuery findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM CupomDesconto WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
        
    }
}
