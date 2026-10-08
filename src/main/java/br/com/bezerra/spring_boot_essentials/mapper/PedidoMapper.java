package br.com.bezerra.spring_boot_essentials.mapper;

import br.com.bezerra.spring_boot_essentials.database.model.ItemPedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.dto.ItemPedidoResponseDTO;
import br.com.bezerra.spring_boot_essentials.dto.PedidoResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PedidoMapper {

    public PedidoResponseDTO toResponseDTO(
            PedidoEntity pedido,
            List<ItemPedidoEntity> itens) {

        PedidoResponseDTO dto =
                new PedidoResponseDTO();

        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setNomeUsuario(pedido.getUsuario().getNome());
        dto.setStatus(pedido.getStatus());

        List<ItemPedidoResponseDTO> itensDTO = itens.stream()
                .map(this::toItemResponseDTO)
                .toList();

        dto.setItens(itensDTO);

        BigDecimal total = itensDTO.stream()
                .map(ItemPedidoResponseDTO::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotal(total);

        return dto;
    }

    private ItemPedidoResponseDTO toItemResponseDTO(
            ItemPedidoEntity item) {

        ItemPedidoResponseDTO dto =
                new ItemPedidoResponseDTO();

        dto.setProdutoId(item.getProduto().getId());
        dto.setNomeProduto(item.getProduto().getNome());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setQuantidade(item.getQuantidade());

        BigDecimal subtotal = item.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));

        dto.setSubTotal(subtotal);

        return dto;
    }

}
