package br.com.bezerra.spring_boot_essentials.service;

import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.UsuarioRepository;
import br.com.bezerra.spring_boot_essentials.exceptions.UsuarioNaoEncontradoException;
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

    public UsuarioEntity atualizar(
            UsuarioEntity usuarioExistente,
            UsuarioEntity usuarioAtualizado){

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());

        return usuarioRepository.save(usuarioExistente);
    }

    public List<UsuarioEntity> findAll(){
        return usuarioRepository.findAll();
    }

    public UsuarioEntity findById(Integer id){

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException(id)
                );
    }

    public void deleteById(Integer id){

        UsuarioEntity usuario = findById(id);

        usuarioRepository.delete(usuario);
    }
}
