package com.softwareLibrary.biblioteca.Specification;

import com.softwareLibrary.biblioteca.DTO.LivroFiltro;
import com.softwareLibrary.biblioteca.Entidade.Livro;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LivroSpecification {

    public static Specification<Livro> comFiltro(LivroFiltro filtro) {
        return (Root<Livro> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por ISBN (exata)
            if (filtro.getIsbn() != null) {
                predicates.add(builder.equal(root.get("isbn"), filtro.getIsbn()));
            }

            // Filtro por título (case insensitive e partial match)
            if (filtro.getTitulo() != null && !filtro.getTitulo().isEmpty()) {
                predicates.add(builder.like(
                        builder.lower(root.get("titulo")),
                        "%" + filtro.getTitulo().toLowerCase() + "%"
                ));
            }

            // Filtro por autores (para ElementCollection)
            if (filtro.getAutores() != null && !filtro.getAutores().isEmpty()) {
                Join<Livro, String> autoresJoin = root.join("autores", JoinType.INNER);
                predicates.add(autoresJoin.in(filtro.getAutores()));
            }

            // Filtro por assuntos (para ElementCollection)
            if (filtro.getAssuntos() != null && !filtro.getAssuntos().isEmpty()) {
                Join<Livro, String> assuntosJoin = root.join("assuntos", JoinType.INNER);
                predicates.add(assuntosJoin.in(filtro.getAssuntos()));
            }

            // Filtro por editora
            if (filtro.getEditora() != null) {
                predicates.add(builder.like(
                        builder.lower(root.get("editora")),
                        "%" + filtro.getEditora().toLowerCase() + "%"
                ));
            }

            // Filtro por ano de publicação
            if (filtro.getAnoPublicacao() != null) {
                predicates.add(builder.equal(root.get("anoPublicacao"), filtro.getAnoPublicacao()));
            }

            // Filtro por língua
            if (filtro.getLingua() != null) {
                predicates.add(builder.equal(root.get("lingua"), filtro.getLingua()));
            }

            // Filtro por local de publicação
            if (filtro.getLocalPublicacao() != null) {
                predicates.add(builder.like(
                        builder.lower(root.get("localPublicacao")),
                        "%" + filtro.getLocalPublicacao().toLowerCase() + "%"
                ));
            }

            // Filtro por número de chamada
            if (filtro.getNumeroChamada() != null) {
                predicates.add(builder.like(
                        builder.lower(root.get("numeroChamada")),
                        "%" + filtro.getNumeroChamada().toLowerCase() + "%"
                ));
            }

            // Filtro por CDD
            if (filtro.getCdd() != null) {
                predicates.add(builder.like(
                        builder.lower(root.get("cdd")),
                        "%" + filtro.getCdd().toLowerCase() + "%"
                ));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
