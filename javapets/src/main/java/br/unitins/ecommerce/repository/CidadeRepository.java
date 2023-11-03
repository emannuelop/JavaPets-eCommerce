package br.unitins.ecommerce.repository;


import br.unitins.ecommerce.model.endereco.Cidade;
import br.unitins.ecommerce.model.produto.produto.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CidadeRepository implements PanacheRepository<Cidade> {

    public PanacheQuery<Cidade> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%");
    }

     public Cidade findByNomeValidation(String nome) {

        if (nome == null)
            return null;

        return find("nome = ?1 ", nome).firstResult();
    }

}
