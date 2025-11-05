package com.softwareLibrary.biblioteca.Service;

import com.softwareLibrary.biblioteca.Entidade.Aluno;
import com.softwareLibrary.biblioteca.Entidade.Emprestimo;
import com.softwareLibrary.biblioteca.Entidade.Livro;
import com.softwareLibrary.biblioteca.Enums.StatusEmprestimo;
import com.softwareLibrary.biblioteca.Repository.EmprestimoRepository;
import com.softwareLibrary.biblioteca.Repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivroRepository livroRepository;

    public Emprestimo realizarEmprestimo(String matriculaAluno, String isbnLivro) {

        // Verificar se aluno existe
        if (!alunoService.existePorMatricula(matriculaAluno)) {
            throw new RuntimeException("Aluno não encontrado com matrícula: " + matriculaAluno);
        }

        // Verificar se livro existe
        if (livroService.buscarPorIsbn(isbnLivro).isEmpty()) {
            throw new RuntimeException("Livro não encontrado com ISBN: " + isbnLivro);
        }

        // Verificar se aluno já tem empréstimo ativo
        if (emprestimoRepository.existsByMatriculaAlunoAndStatus(matriculaAluno, "PENDENTE")) {
            throw new RuntimeException("Aluno já possui um empréstimo pendente");
        }

        if (emprestimoRepository.existsByMatriculaAlunoAndStatus(matriculaAluno, "ATRASADO")) {
            throw new RuntimeException("Aluno já possui um empréstimo atrasado");
        }

        // Verificar se livro já está emprestado
        if (emprestimoRepository.findByIsbnLivroAndStatus(isbnLivro, "PENDENTE").isPresent()) {
            throw new RuntimeException("Livro já está emprestado");
        }

        Emprestimo emprestimo = new Emprestimo(matriculaAluno, isbnLivro);
        return emprestimoRepository.save(emprestimo);
    }

    //Errado
    public Emprestimo devolverLivro(Long emprestimoId) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimoId);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            if ("ATIVO".equals(emprestimo.getStatus())) {
                emprestimo.devolver();
                return emprestimoRepository.save(emprestimo);
            } else {
                throw new RuntimeException("Empréstimo já está devolvido");
            }
        }
        throw new RuntimeException("Empréstimo não encontrado");
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public List<Emprestimo> listarAtivos() {
        return emprestimoRepository.findByStatus("PENDENTE");
    }

    public List<Emprestimo> listarAtrasados() {
        return emprestimoRepository.findEmprestimosAtrasados();
    }

    public List<Emprestimo> historicoPorAluno(String matriculaAluno) {
        return emprestimoRepository.findByMatriculaAlunoOrderByDataRetiradaDesc(matriculaAluno);
    }

    public List<Emprestimo> historicoPorLivro(String isbnLivro) {
        return emprestimoRepository.findByIsbnLivroOrderByDataRetiradaDesc(isbnLivro);
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public Optional<Emprestimo> buscarEmprestimoAtivoPorAluno(String matriculaAluno) {
        List<Emprestimo> ativos = emprestimoRepository.findByMatriculaAlunoAndStatus(matriculaAluno, "EMPRESTINO");
        return ativos.isEmpty() ? Optional.empty() : Optional.of(ativos.get(0));
    }


}