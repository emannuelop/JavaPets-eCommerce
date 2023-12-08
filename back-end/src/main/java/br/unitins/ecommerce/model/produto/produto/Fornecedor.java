package br.unitins.ecommerce.model.produto.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import br.unitins.ecommerce.model.DefaultEntity;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToOne;
// import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Telefone;

@Entity
public class Fornecedor extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "id_telefone", unique = true)
    private Telefone telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
