package br.com.bezerra.spring_boot_essentials.database.repository;

import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>{

}

