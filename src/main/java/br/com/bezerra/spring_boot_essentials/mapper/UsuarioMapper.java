package br.com.bezerra.spring_boot_essentials.mapper;

import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.dto.UsuarioRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioEntity toEntity(UsuarioRequestDTO dto){

        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(UsuarioEntity usuario){

        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());

        return dto;
    }

}

