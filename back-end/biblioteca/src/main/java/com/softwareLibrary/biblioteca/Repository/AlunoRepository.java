package com.softwareLibrary.biblioteca.Repository;

import com.softwareLibrary.biblioteca.Entidade.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String> {
    boolean existsByMatricula(String matricula);
}
