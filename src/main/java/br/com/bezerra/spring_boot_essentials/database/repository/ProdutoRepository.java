package br.com.bezerra.spring_boot_essentials.database.repository;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {

    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);

    List<ProdutoEntity> findByPrecoBetween(
            BigDecimal min,
            BigDecimal max
    );
}
