package br.com.bezerra.spring_boot_essentials.service;

import br.com.bezerra.spring_boot_essentials.database.model.ItemPedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.database.model.enums.StatusPedido;
import br.com.bezerra.spring_boot_essentials.database.repository.ItemPedidoRepository;
import br.com.bezerra.spring_boot_essentials.database.repository.PedidoRepository;
import br.com.bezerra.spring_boot_essentials.dto.ItemPedidoRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.PedidoRequestDTO;
import br.com.bezerra.spring_boot_essentials.exceptions.PedidoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Transactional
    public PedidoEntity criar(PedidoRequestDTO pedidoDTO) {

        Set<Integer> produtosAdicionados = new HashSet<>();

        for (ItemPedidoRequestDTO itemDTO : pedidoDTO.getItens()) {

            if (!produtosAdicionados.add(itemDTO.getProdutoId())) {

                throw new IllegalArgumentException(
                        "O produto de ID " + itemDTO.getProdutoId()
                                + " está duplicado no pedido"
                );
            }
        }

        UsuarioEntity usuario =
                usuarioService.findById(pedidoDTO.getUsuarioId());

        PedidoEntity pedido = new PedidoEntity();

        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.CRIADO);

        PedidoEntity pedidoSalvo =
                pedidoRepository.save(pedido);

        for (ItemPedidoRequestDTO itemDTO : pedidoDTO.getItens()) {

            ProdutoEntity produto =
                    produtoService.findById(itemDTO.getProdutoId());

            if (produto.getQuantidade() < itemDTO.getQuantidade()) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para o produto: " + produto.getNome()
                );
            }

            ItemPedidoEntity item = new ItemPedidoEntity();

            item.setPedido(pedidoSalvo);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            itemPedidoRepository.save(item);

            produto.setQuantidade(
                    produto.getQuantidade()
                            - itemDTO.getQuantidade()
            );

            produtoService.salvar(produto);
        }

        return pedidoSalvo;
    }

    public List<PedidoEntity> findAll() {
        return pedidoRepository.findAll();
    }

    public List<ItemPedidoEntity> buscarItens(Integer pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }

    public PedidoEntity findById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new PedidoNaoEncontradoException(id)
                );
    }

    @Transactional
    public PedidoEntity atualizarStatus(
            Integer pedidoId,
            StatusPedido novoStatus) {

        PedidoEntity pedido = findById(pedidoId);

        StatusPedido statusAtual = pedido.getStatus();

        if (statusAtual == null) {
            throw new IllegalArgumentException(
                    "O pedido não possui um status definido"
            );
        }

        if (statusAtual == StatusPedido.CRIADO
                && novoStatus == StatusPedido.CONFIRMADO) {

            pedido.setStatus(novoStatus);

        } else if (statusAtual == StatusPedido.CONFIRMADO
                && novoStatus == StatusPedido.ENTREGUE) {

            pedido.setStatus(novoStatus);

        } else {
            throw new IllegalArgumentException(
                    "Não é permitido alterara o status de "
                            + statusAtual + " para " + novoStatus);
        }

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public PedidoEntity cancelarPedido(Integer pedidoId) {

        PedidoEntity pedido = findById(pedidoId);

        if (pedido.getStatus() != StatusPedido.CRIADO
                && pedido.getStatus() != StatusPedido.CONFIRMADO) {

            throw new IllegalArgumentException(
                    "Não é possível cancelar um pedido com status: "
                            + pedido.getStatus()
            );
        }

        List<ItemPedidoEntity> itens =
                itemPedidoRepository.findByPedidoId(pedidoId);

        for (ItemPedidoEntity item : itens){

            ProdutoEntity produto = item.getProduto();

            produto.setQuantidade(
                    produto.getQuantidade() + item.getQuantidade()
            );

            produtoService.salvar(produto);
        }

        pedido.setStatus(StatusPedido.CANCELADO);

        return pedidoRepository.save(pedido);
    }
}
