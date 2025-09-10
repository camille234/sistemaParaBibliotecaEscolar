package com.softwareLibrary.biblioteca.Entidade;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn", unique = true, length = 17)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "###-######-##-#")
    private String isbn;

    @Column(name = "numero_chamada", length = 50)
    private String numeroChamada;

    @Column(name = "numero_registro", unique = true)
    private Integer numeroRegistro;

    @Column(name = "lingua", length = 20)
    private String lingua;

    @ElementCollection
    @CollectionTable(name = "livro_autores", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "autor")
    private List<String> autores;

    @Column(name = "titulo", length = 500)
    private String titulo;

    @Column(name = "edicao", length = 50)
    private String edicao;

    @Column(name = "local_publicacao", length = 100)
    private String localPublicacao;

    @Column(name = "editora", length = 100)
    private String editora;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "descricao_fisica", length = 200)
    private String descricaoFisica;

    @Column(name = "titulo_serie", length = 200)
    private String tituloSerie;

    @ElementCollection
    @CollectionTable(name = "livro_assuntos", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "assunto")
    private List<String> assuntos;

    @Column(name = "cutter", length = 50)
    private String cutter;

    @Column(name = "cdd", length = 20)
    private String cdd;

    // Construtores
    public Livro() {
    }

    public Livro(String isbn, String numeroChamada, Integer numeroRegistro,
                 String lingua, List<String> autores, String titulo) {
        this.isbn = isbn;
        this.numeroChamada = numeroChamada;
        this.numeroRegistro = numeroRegistro;
        this.lingua = lingua;
        this.autores = autores;
        this.titulo = titulo;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(String numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public Integer getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getLocalPublicacao() {
        return localPublicacao;
    }

    public void setLocalPublicacao(String localPublicacao) {
        this.localPublicacao = localPublicacao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getDescricaoFisica() {
        return descricaoFisica;
    }

    public void setDescricaoFisica(String descricaoFisica) {
        this.descricaoFisica = descricaoFisica;
    }

    public String getTituloSerie() {
        return tituloSerie;
    }

    public void setTituloSerie(String tituloSerie) {
        this.tituloSerie = tituloSerie;
    }

    public List<String> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<String> assuntos) {
        this.assuntos = assuntos;
    }

    public String getCutter() {
        return cutter;
    }

    public void setCutter(String cutter) {
        this.cutter = cutter;
    }

    public String getCdd() {
        return cdd;
    }

    public void setCdd(String cdd) {
        this.cdd = cdd;
    }

    // Métodos auxiliares
    public String getAutoresFormatados() {
        return String.join("; ", autores);
    }

    public String getAssuntosFormatados() {
        return String.join("; ", assuntos);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autores=" + autores +
                '}';
    }
}