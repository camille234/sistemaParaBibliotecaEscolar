package com.softwareLibrary.biblioteca.DTO;

public class LoginResponseDto {

    private String mensagem;

    public LoginResponseDto(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}
