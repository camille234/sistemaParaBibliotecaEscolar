package com.softwareLibrary.biblioteca.Service;

import com.softwareLibrary.biblioteca.DTO.AssuntoDto;
import com.softwareLibrary.biblioteca.DTO.LivroFiltro;
import com.softwareLibrary.biblioteca.Entidade.Livro;
import com.softwareLibrary.biblioteca.Repository.EmprestimoRepository;
import com.softwareLibrary.biblioteca.Repository.LivroRepository;
import com.softwareLibrary.biblioteca.Specification.LivroSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Livro salvar(Livro livro) {
        try {
            // Garantir que as listas não sejam nulas
            if (livro.getAutores() == null) {
                livro.setAutores(new ArrayList<>());
            }
            if (livro.getAssuntos() == null) {
                livro.setAssuntos(new ArrayList<>());
            }

            return livroRepository.save(livro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar livro: " + e.getMessage(), e);
        }
    }

    public List<Livro> listaLivro(){
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id){
        return livroRepository.findById(id);
    }

    public Optional<Livro> buscarPorIsbn(String isbn) {
        // Remove formatação do ISBN para busca
        //String isbnLimpo = isbn.replace("-", "");
        return livroRepository.findByIsbnContaining(isbn);
    }

    public boolean isLivroDisponivel(String isbn) {
        Optional<Livro> livroOpt = buscarPorIsbn(isbn);
        if (livroOpt.isEmpty()) {
            return false;
        }

        Livro livro = livroOpt.get();

        // Se não há exemplares, não está disponível
        if (livro.getExemplares() == null || livro.getExemplares() <= 0) {
            return false;
        }

        // Conta quantos empréstimos ativos existem para este ISBN
        long emprestimosAtivos = emprestimoRepository.countByIsbnLivroAndStatusIn(
                isbn, Arrays.asList("PENDENTE", "ATRASADO"));

        // Verifica se há exemplares disponíveis
        return livro.getExemplares() > emprestimosAtivos;
    }

    public void removerPorId(Long id){
        livroRepository.deleteById(id);
    }

    public List<Livro> filtrar(LivroFiltro filtro) {
        Specification<Livro> spec = LivroSpecification.comFiltro(filtro);
        return livroRepository.findAll(spec);
    }

    public boolean existePorIsbn(String isbn) {
        String isbnLimpo = isbn.replace("-", "");
        return livroRepository.existsByIsbn(isbnLimpo);
    }

    // Metodo auxiliar para formatar ISBN
    private String formatarIsbn(String isbn) {
        if (isbn == null) return null;
        return isbn.replace("-", ""); // Remove hífens para armazenamento
    }

//    public List<AssuntoDto> listarAssuntos(){
//        return livroRepository.findAllCategorias();
//    }

}
