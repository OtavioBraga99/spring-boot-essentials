package br.com.bezerra.spring_boot_essentials.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException(Integer id){
        super("Usuario não encontrado com id: " + id);
    }
}
