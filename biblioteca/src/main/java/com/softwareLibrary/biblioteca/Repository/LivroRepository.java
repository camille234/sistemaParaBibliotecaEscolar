package com.softwareLibrary.biblioteca.Repository;

import com.softwareLibrary.biblioteca.Entidade.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface LivroRepository extends JpaRepository<Livro, Long> {
}

