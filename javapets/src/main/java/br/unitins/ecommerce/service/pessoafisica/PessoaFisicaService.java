package br.unitins.ecommerce.service.pessoafisica;

import br.unitins.ecommerce.dto.usuario.PessoaFisicaDTO;
import br.unitins.ecommerce.model.usuario.PessoaFisica;
import br.unitins.ecommerce.model.usuario.Usuario;
import jakarta.validation.Valid;

public interface PessoaFisicaService {
    
    PessoaFisica insertPessoaFisica (@Valid PessoaFisicaDTO pessoaFisicaDTO);

    PessoaFisica insertPessoaFisica (String nome, String email);

    void updatePessoaFisica (Usuario usuario, @Valid PessoaFisicaDTO pessoaFisicaDTO);
}
