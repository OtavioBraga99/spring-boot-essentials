package br.com.bezerra.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotNull;

public class PedidoRequestDTO {

    @NotNull(message = "O usuário é obrigatório")
    private Integer usuarioId;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
