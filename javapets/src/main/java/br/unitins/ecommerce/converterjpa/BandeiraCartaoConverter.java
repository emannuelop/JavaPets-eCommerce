package br.unitins.ecommerce.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import br.unitins.ecommerce.model.pagamento.BandeiraCartao;

@Converter(autoApply = true)
public class BandeiraCartaoConverter implements AttributeConverter<BandeiraCartao, Integer>{

    @Override
    public Integer convertToDatabaseColumn(BandeiraCartao bandeiraCartao) {
        return bandeiraCartao == null ? null : bandeiraCartao.getId();
    }

    @Override
    public BandeiraCartao convertToEntityAttribute(Integer id) {
        return BandeiraCartao.valueOf(id);
    }
}
