package com.softwareLibrary.biblioteca.Service;

import com.softwareLibrary.biblioteca.Entidade.Emprestimo;
import com.softwareLibrary.biblioteca.Repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final AlunoService alunoService;
    private final LivroService livroService;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             AlunoService alunoService,
                             LivroService livroService) {
        this.emprestimoRepository = emprestimoRepository;
        this.alunoService = alunoService;
        this.livroService = livroService;
    }

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
        if (emprestimoRepository.existsByMatriculaAlunoAndStatus(matriculaAluno, "ATIVO")) {
            throw new RuntimeException("Aluno já possui um empréstimo ativo");
        }

        // Verificar se livro já está emprestado
        if (emprestimoRepository.findByIsbnLivroAndStatus(isbnLivro, "ATIVO").isPresent()) {
            throw new RuntimeException("Livro já está emprestado");
        }

        Emprestimo emprestimo = new Emprestimo(matriculaAluno, isbnLivro);
        return emprestimoRepository.save(emprestimo);
    }

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

    public Emprestimo devolverLivroPorIsbn(String isbnLivro) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findByIsbnLivroAndStatus(isbnLivro, "ATIVO");
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            emprestimo.devolver();
            return emprestimoRepository.save(emprestimo);
        }
        throw new RuntimeException("Não há empréstimo ativo para este livro");
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public List<Emprestimo> listarAtivos() {
        return emprestimoRepository.findByStatus("ATIVO");
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
        List<Emprestimo> ativos = emprestimoRepository.findByMatriculaAlunoAndStatus(matriculaAluno, "ATIVO");
        return ativos.isEmpty() ? Optional.empty() : Optional.of(ativos.get(0));
    }

    public void atualizarStatusAtrasados() {
        List<Emprestimo> atrasados = emprestimoRepository.findEmprestimosAtrasados();
        for (Emprestimo emprestimo : atrasados) {
            emprestimo.setStatus("ATRASADO");
            emprestimoRepository.save(emprestimo);
        }
    }
}