package br.com.bezerra.spring_boot_essentials.service;

import br.com.bezerra.spring_boot_essentials.database.model.PedidoEntity;
import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioService usuarioService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioService = usuarioService;
    }

    public PedidoEntity criar(Integer usuarioId){

        UsuarioEntity usuario =
                usuarioService.findById(usuarioId);

        PedidoEntity pedido = new PedidoEntity();

        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());

        return pedidoRepository.save(pedido);
    }

    public List<PedidoEntity> findAll(){
        return pedidoRepository.findAll();
    }
}
