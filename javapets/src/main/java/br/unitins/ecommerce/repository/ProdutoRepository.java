package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.ecommerce.model.produto.produto.Marca;
import br.unitins.ecommerce.model.produto.produto.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByNome(String nome) {

        if (nome == null)
            return null;

        return find("FROM Produto WHERE UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Produto> findByMarca(Marca marca) {

        if (marca == null)
            return null;

        return find("FROM Produto WHERE marca = ?1", marca).list();
    }

    public List<Produto> filterByPrecoMinimo(Double preco) {

        if (preco == null)
            return null;

        return find("FROM Produto WHERE preco > ?1", preco).list();
    }

    public List<Produto> filterByPrecoMaximo(Double preco) {

        if (preco == null)
            return null;

        return find("FROM Produto WHERE preco < ?1", preco).list();
    }

    public List<Produto> filterByEntrePreco(Double precoMin, Double precoMax) {

        if (precoMin == null || precoMax == null)
            return null;

        return find("FROM Produto WHERE preco > ?1 AND preco < ?2", precoMin, precoMax).list();
    }

}
