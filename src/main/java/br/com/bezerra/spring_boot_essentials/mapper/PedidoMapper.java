package br.com.bezerra.spring_boot_essentials.mapper;

import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.dto.PedidoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoResponseDTO toResponseDTO(PedidoEntity pedido){

        PedidoResponseDTO dto =
                new PedidoResponseDTO();

        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setNomeUsuario(pedido.getUsuario().getNome());

        return dto;
    }
}
