package com.softwareLibrary.biblioteca.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Senha fixa - pode ser configurada no application.properties
    @Value("${bibliotecaria.senha:123456}") // Valor padrão "123456" se não configurado
    private String senhaSistema;

    public boolean authenticate(String senhaDigitada) {
        if (senhaDigitada == null || senhaDigitada.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode estar vazia");
        }

        return senhaSistema.equals(senhaDigitada);
    }

    // Método para alterar a senha (opcional)
    public void setSenhaSistema(String novaSenha) {
        this.senhaSistema = novaSenha;
    }
}