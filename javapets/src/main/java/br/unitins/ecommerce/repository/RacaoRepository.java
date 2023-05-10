package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.produto.Marca;
import br.unitins.ecommerce.model.produto.racao.EscolhaAnimal;
import br.unitins.ecommerce.model.produto.racao.Racao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class RacaoRepository implements PanacheRepository<Racao> {

    public List<Racao> findByNome(String nome) {

        if (nome == null)
            return null;

        return find("FROM Racao WHERE UNACCENT(UPPER(nome)) LIKE UNACCENT(?1)", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Racao> findByEscolhaAnimal(EscolhaAnimal escolhaAnimal) {

        if (escolhaAnimal == null)
            return null;

        return find("FROM Racao WHERE escolhaAnimal = ?1", escolhaAnimal).list();
    }

    public List<Racao> findByMarca(Marca marca) {

        if (marca == null)
            return null;

        return find("FROM Racao WHERE marca = ?1", marca).list();
    }

    public List<Racao> filterByPrecoMinimo(Double preco) {

        if (preco == null)
            return null;

        return find("FROM Racao WHERE preco > ?1", preco).list();
    }

    public List<Racao> filterByPrecoMaximo(Double preco) {

        if (preco == null)
            return null;

        return find("FROM Racao WHERE preco < ?1", preco).list();
    }

    public List<Racao> filterByEntrePreco(Double precoMin, Double precoMax) {

        if (precoMin == null || precoMax == null)
            return null;

        return find("FROM Racao WHERE preco > ?1 AND preco < ?2", precoMin, precoMax).list();
    }

}
