package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.compra.CupomDesconto;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CupomDescontoRepository implements PanacheRepository<CupomDesconto> {
    
    public PanacheQuery<CupomDesconto> findByNome (String codigoCupom) {

        if (codigoCupom == null)
            return null;

        return find("FROM CupomDesconto WHERE UPPER(codigoCupom) LIKE ?1", "%" + codigoCupom.toUpperCase() + "%");
        
    }

    public CupomDesconto findByNomeValidation (String codigoCupom) {

        if (codigoCupom == null)
            return null;

            return find("codigoCupom = ?1 ", codigoCupom).firstResult();
        
    }
}
