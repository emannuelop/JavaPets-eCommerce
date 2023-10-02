package br.unitins.ecommerce.model.produto.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Categoria extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
