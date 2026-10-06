package br.com.bezerra.spring_boot_essentials.service;

import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity salvar(UsuarioEntity usuario){
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioEntity> findAll(){
        return usuarioRepository.findAll();
    }
}
