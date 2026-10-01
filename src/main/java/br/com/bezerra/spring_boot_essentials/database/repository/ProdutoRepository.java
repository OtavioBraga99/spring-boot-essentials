package br.com.bezerra.spring_boot_essentials.database.repository;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {


}
