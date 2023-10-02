package br.unitins.ecommerce.repository;

import java.util.List;

import br.unitins.ecommerce.model.compra.CupomDesconto;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CupomDescontoRepository implements PanacheRepository<CupomDesconto> {
    
    public List<CupomDesconto> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM CupomDesconto WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
