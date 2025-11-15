package com.softwareLibrary.biblioteca.Repository;


import com.softwareLibrary.biblioteca.Entidade.Emprestimo;
import com.softwareLibrary.biblioteca.Enums.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Encontrar empréstimos ativos por matrícula
    List<Emprestimo> findByMatriculaAlunoAndStatus(String matriculaAluno, String status);

    // Verificar se aluno tem empréstimo ativo
    boolean existsByMatriculaAlunoAndStatus(String matriculaAluno, String status);

    // Encontrar empréstimo ativo por ISBN
    Optional<Emprestimo> findByIsbnLivroAndStatus(String isbnLivro, String status);

    // Encontrar todos os empréstimos ativos
    List<Emprestimo> findByStatus(String status);

    List<Emprestimo> findByStatusIn(List<String> status);


    // Histórico de empréstimos por aluno
    List<Emprestimo> findByMatriculaAlunoOrderByDataRetiradaDesc(String matriculaAluno);

    Optional<Emprestimo> findByMatriculaAluno(String matricula);

    // Histórico de empréstimos por livro
    List<Emprestimo> findByIsbnLivroOrderByDataRetiradaDesc(String isbnLivro);

    long countByIsbnLivroAndStatusIn(String isbnLivro, List<String> status);

    boolean existsByMatriculaAlunoAndStatusIn(String matriculaAluno, List<String> status);

}