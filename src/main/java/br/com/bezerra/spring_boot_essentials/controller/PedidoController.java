package br.com.bezerra.spring_boot_essentials.controller;

import br.com.bezerra.spring_boot_essentials.database.model.ItemPedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.dto.AtualizarStatusPedidoDTO;
import br.com.bezerra.spring_boot_essentials.dto.PedidoRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.PedidoResponseDTO;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoResponseDTO;
import br.com.bezerra.spring_boot_essentials.mapper.PedidoMapper;
import br.com.bezerra.spring_boot_essentials.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criar(
            @Valid @RequestBody PedidoRequestDTO pedidoDTO) {

        PedidoEntity pedido =
                pedidoService.criar(pedidoDTO);

        List<ItemPedidoEntity> itens =
                pedidoService.buscarItens(pedido.getId());

        return pedidoMapper.toResponseDTO(pedido, itens);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResponseDTO> findAll() {

        List<PedidoEntity> pedidos =
                pedidoService.findAll();

        return pedidos.stream()
                .map(pedido -> {

                    List<ItemPedidoEntity> itens =
                            pedidoService.buscarItens(pedido.getId());

                    return pedidoMapper.toResponseDTO(
                            pedido,
                            itens
                    );
                })
                .toList();
    }

    @PatchMapping("/{id}/status")
    public PedidoResponseDTO atualizarStatus(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizarStatusPedidoDTO dto) {

        PedidoEntity pedido =
                pedidoService.atualizarStatus(id, dto.getStatus());

        List<ItemPedidoEntity> itens =
                pedidoService.buscarItens(pedido.getId());

        return pedidoMapper.toResponseDTO(pedido, itens);
    }

    @PatchMapping("/{id}/cancelar")
    public PedidoResponseDTO cancelarPedido(@PathVariable Integer id){

        PedidoEntity pedido = pedidoService.cancelarPedido(id);

        List<ItemPedidoEntity> itens =
                pedidoService.buscarItens(pedido.getId());

        return pedidoMapper.toResponseDTO(pedido, itens);
    }
}
