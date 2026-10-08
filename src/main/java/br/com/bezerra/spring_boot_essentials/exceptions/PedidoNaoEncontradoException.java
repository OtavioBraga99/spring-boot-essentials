package br.com.bezerra.spring_boot_essentials.exceptions;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(Integer id){
        super("Pedido não encontrado com id: " + id);
    }
}
