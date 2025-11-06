package com.softwareLibrary.biblioteca.DTO;

import java.util.List;

public class LivroFiltro {

    private String isbn;
    private String titulo;
    private List<String> autores;
    private List<String> assuntos;
    private String editora;
    private Integer anoPublicacao;
    private String lingua;
    private String localPublicacao;
    private String numeroChamada;
    private String cdd;
    private Boolean disponivel;

    // Getters e Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<String> getAutores() { return autores; }
    public void setAutores(List<String> autores) { this.autores = autores; }

    public List<String> getAssuntos() { return assuntos; }
    public void setAssuntos(List<String> assuntos) { this.assuntos = assuntos; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getLingua() { return lingua; }
    public void setLingua(String lingua) { this.lingua = lingua; }

    public String getLocalPublicacao() { return localPublicacao; }
    public void setLocalPublicacao(String localPublicacao) { this.localPublicacao = localPublicacao; }

    public String getNumeroChamada() { return numeroChamada; }
    public void setNumeroChamada(String numeroChamada) { this.numeroChamada = numeroChamada; }

    public String getCdd() { return cdd; }
    public void setCdd(String cdd) { this.cdd = cdd; }

    public Boolean getDisponivel() {
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
}
