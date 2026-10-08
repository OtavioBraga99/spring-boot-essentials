package br.com.bezerra.spring_boot_essentials.dto;

import br.com.bezerra.spring_boot_essentials.database.model.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusPedidoDTO {

    @NotNull(message = "O status é obrigatório")
    private StatusPedido status;

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}
