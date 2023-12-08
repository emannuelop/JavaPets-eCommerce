package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.endereco.Estado;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {
    
    public PanacheQuery<Estado> findByNome(String nome) {

        if (nome == null)
            return null;

        return find("FROM Estado WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public List<Estado> findBySigla(String sigla) {

        if (sigla == null)
            return null;

        return find("FROM Estado WHERE UPPER(sigla) LIKE ?1", "%" + sigla.toUpperCase() + "%").list();
    }
}
