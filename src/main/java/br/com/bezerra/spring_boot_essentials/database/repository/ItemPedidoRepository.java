package br.com.bezerra.spring_boot_essentials.database.repository;

import br.com.bezerra.spring_boot_essentials.database.model.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoEntity, Integer> {

    List<ItemPedidoEntity> findByPedidoId(Integer pedidoId);
}
