package com.softwareLibrary.biblioteca.DTO;

public class AlunoLoginResponseDto {

    private String mensagem;
    private String matricula;
    private String nome;
    private String turma;

    public AlunoLoginResponseDto(String mensagem, String matricula, String nome, String turma) {
        this.mensagem = mensagem;
        this.matricula = matricula;
        this.nome = nome;
        this.turma = turma;
    }

    // Getters
    public String getMensagem() { return mensagem; }
    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public String getTurma() { return turma; }

    // Setters
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTurma(String turma) { this.turma = turma; }
}

