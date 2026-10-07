package br.com.bezerra.spring_boot_essentials.service;

import br.com.bezerra.spring_boot_essentials.database.model.ItemPedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.ItemPedidoRepository;
import br.com.bezerra.spring_boot_essentials.database.repository.PedidoRepository;
import br.com.bezerra.spring_boot_essentials.dto.ItemPedidoRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.PedidoRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            UsuarioService usuarioService,
            ProdutoService produtoService,
            ItemPedidoRepository itemPedidoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public PedidoEntity criar(PedidoRequestDTO pedidoDTO) {

        UsuarioEntity usuario =
                usuarioService.findById(pedidoDTO.getUsuarioId());

        PedidoEntity pedido = new PedidoEntity();

        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());

        PedidoEntity pedidoSalvo =
                pedidoRepository.save(pedido);

        for (ItemPedidoRequestDTO itemDTO : pedidoDTO.getItens()) {

            ProdutoEntity produto =
                    produtoService.findById(itemDTO.getProdutoId());

            ItemPedidoEntity item = new ItemPedidoEntity();

            item.setPedido(pedidoSalvo);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            itemPedidoRepository.save(item);
        }

        return pedidoSalvo;

    }

    public List<PedidoEntity> findAll() {
        return pedidoRepository.findAll();
    }

    public List<ItemPedidoEntity> buscarItens(Integer pedidoId){
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }
}
