package br.com.bezerra.spring_boot_essentials.mapper;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoEntity toEntity(ProdutoRequestDTO dto){

        ProdutoEntity produto = new ProdutoEntity();

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());

        return produto;
    }

    public ProdutoResponseDTO toResponseDTO(ProdutoEntity produto){

        ProdutoResponseDTO dto = new ProdutoResponseDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());

        return dto;
    }

}
