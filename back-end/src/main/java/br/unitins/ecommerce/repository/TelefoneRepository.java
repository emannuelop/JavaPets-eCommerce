package br.unitins.ecommerce.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {
    
    public List<Telefone> findByListId (List<Long> ids) {

        if (ids == null)
            return null;

        List<Telefone> telefones = new ArrayList<>();

        for (Long id : ids) {
            
            telefones.add(findById(id));
        }

        return telefones;
    }    

    public List<Telefone> findTelefoneByUsuario(Usuario usuario) {
    if (usuario == null) {
        return null;
    }
    return find("FROM Telefone WHERE usuario = ?1", usuario).list();
}
}
