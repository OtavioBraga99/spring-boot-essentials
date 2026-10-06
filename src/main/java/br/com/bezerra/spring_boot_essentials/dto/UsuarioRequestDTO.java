package br.com.bezerra.spring_boot_essentials.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UsuarioRequestDTO {

    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
