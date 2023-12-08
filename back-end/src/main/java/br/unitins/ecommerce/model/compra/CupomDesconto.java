package br.unitins.ecommerce.model.compra;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class CupomDesconto extends DefaultEntity {

    @Column(nullable = false)
    private String codigoCupom;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;

    @Column(nullable = false)
    private Integer porcentagemDesconto;

    public String getCodigoCupom() {
        return codigoCupom;
    }

    public void setCodigoCupom(String codigoCupom) {
        this.codigoCupom = codigoCupom;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Integer getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(Integer porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }



}
