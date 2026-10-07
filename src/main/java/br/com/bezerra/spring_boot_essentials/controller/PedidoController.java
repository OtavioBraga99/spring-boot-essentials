package br.com.bezerra.spring_boot_essentials.controller;

import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
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
            @Valid @RequestBody PedidoRequestDTO pedidoDTO){

        PedidoEntity pedido =
                pedidoService.criar(pedidoDTO.getUsuarioId());

        return pedidoMapper.toResponseDTO(pedido);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResponseDTO> findAll(){

        List<PedidoEntity> pedidos =
                pedidoService.findAll();

        return pedidos.stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }
}
