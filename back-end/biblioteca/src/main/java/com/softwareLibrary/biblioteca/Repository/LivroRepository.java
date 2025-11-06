package com.softwareLibrary.biblioteca.Repository;

import com.softwareLibrary.biblioteca.DTO.AssuntoDto;
import com.softwareLibrary.biblioteca.Entidade.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {

    // CORRETO: Retorna um Optional<Livro> pelo ISBN
    Optional<Livro> findByIsbn(String isbn);

    // Metodo para verificar se ISBN já existe (útil para validações)
    boolean existsByIsbn(String isbn);

    // Metodo para buscar por ISBN contendo (para buscas parciais)
    Optional<Livro> findByIsbnContaining(String isbn);
//
//    @Query("SELECT DISTINCT la.assunto FROM LivroAssunto la")
//    List<AssuntoDto> findAllCategorias();

}

