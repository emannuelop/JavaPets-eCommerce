package br.unitins.ecommerce.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.ecommerce.model.produto.racao.EscolhaAnimal;

@Converter(autoApply = true)
public class EscolhaAnimalConverter implements AttributeConverter<EscolhaAnimal, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(EscolhaAnimal escolhaAnimal) {
        return escolhaAnimal == null ? null : escolhaAnimal.getId();
    }

    @Override
    public EscolhaAnimal convertToEntityAttribute(Integer id) {
        return EscolhaAnimal.valueOf(id);
    }

}
