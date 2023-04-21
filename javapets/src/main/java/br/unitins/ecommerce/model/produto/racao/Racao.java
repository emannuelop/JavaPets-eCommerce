package br.unitins.ecommerce.model.produto.racao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import br.unitins.ecommerce.model.produto.Produto;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Racao extends Produto {

    @Column(nullable = false)
    private Double quantidadeQuilos;

    @Column(nullable = false)
    private String sabor;

    private EscolhaAnimal escolhaAnimal;

    public Double getQuantidadeQuilos() {
        return quantidadeQuilos;
    }

    public void setQuantidadeQuilos(Double quantidadeQuilos) {
        this.quantidadeQuilos = quantidadeQuilos;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public EscolhaAnimal getEscolhaAnimal() {
        return escolhaAnimal;
    }

    public void setEscolhaAnimal(EscolhaAnimal escolhaAnimal) {
        this.escolhaAnimal = escolhaAnimal;
    }

}
