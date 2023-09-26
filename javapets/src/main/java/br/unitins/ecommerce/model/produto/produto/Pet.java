package br.unitins.ecommerce.model.produto.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Pet extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 18)
    private String especie;

    @Column(nullable = false, unique = true, length = 18)
    private String raca;

    @Column(nullable = false, unique = true, length = 18)
    private int idadeEmMeses;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdadeEmMeses() {
        return idadeEmMeses;
    }

    public void setIdadeEmMeses(int idadeEmMeses) {
        this.idadeEmMeses = idadeEmMeses;
    }

    

}
