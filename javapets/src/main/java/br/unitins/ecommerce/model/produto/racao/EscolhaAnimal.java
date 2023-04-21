package br.unitins.ecommerce.model.produto.racao;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EscolhaAnimal {

    GATO(1, "Gato"),
    CACHORRO(2, "Cachorro");

    private int id;
    private String label;

    EscolhaAnimal(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static EscolhaAnimal valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (EscolhaAnimal escolhaAnimal : EscolhaAnimal.values()) {
            if (id.equals(escolhaAnimal.getId())) {
                return escolhaAnimal;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }

}
