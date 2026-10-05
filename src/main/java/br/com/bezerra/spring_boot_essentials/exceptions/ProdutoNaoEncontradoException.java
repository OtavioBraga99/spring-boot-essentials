package br.com.bezerra.spring_boot_essentials.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Integer id) {
        super("Produto não encontrado com id: " + id);
    }
}