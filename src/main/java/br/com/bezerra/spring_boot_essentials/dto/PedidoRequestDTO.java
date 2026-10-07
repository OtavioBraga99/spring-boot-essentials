package br.com.bezerra.spring_boot_essentials.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequestDTO {

    @NotNull(message = "O usuário é obrigatório")
    private Integer usuarioId;

    @Valid
    @NotEmpty(message = "O pedido deve possuir pelo menos um item")
    private List<ItemPedidoRequestDTO> itens;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
}
