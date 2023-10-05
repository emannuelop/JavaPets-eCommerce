package br.unitins.ecommerce.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Pet extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 18)
    private String especie;

    @Column(nullable = false, length = 18)
    private String raca;

    @Column(nullable = false, length = 18)
    private Integer idadeEmMeses;

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

    public Integer getIdadeEmMeses() {
        return idadeEmMeses;
    }

    public void setIdadeEmMeses(Integer idadeEmMeses) {
        this.idadeEmMeses = idadeEmMeses;
    }

}
