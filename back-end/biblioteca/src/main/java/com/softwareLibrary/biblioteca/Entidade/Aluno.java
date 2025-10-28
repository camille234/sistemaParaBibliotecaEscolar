package com.softwareLibrary.biblioteca.Entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_alunos")
public class Aluno {

    @Id
    @Column(name = "matricula", unique = true, length = 10)
    private String matricula;

    @Column(name = "turma", length = 50, nullable = false)
    private String turma;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    // Construtores
    public Aluno() {
    }

    public Aluno(String matricula, String turma, String nome) {
        this.matricula = matricula;
        this.turma = turma;
        this.nome = nome;
    }

    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Metodos auxiliares
    @Override
    public String toString() {
        return "Aluno{" +
                "matricula='" + matricula + '\'' +
                ", turma='" + turma + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    // Metodo para validar o formato da matrícula (opcional)
    public boolean isMatriculaValida() {
        return matricula != null && matricula.matches("\\d{10}");
    }
}
