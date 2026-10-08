package br.com.bezerra.spring_boot_essentials.database.repository;

import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer> {

    List<PedidoEntity> findByUsuarioId(Integer id);
}
