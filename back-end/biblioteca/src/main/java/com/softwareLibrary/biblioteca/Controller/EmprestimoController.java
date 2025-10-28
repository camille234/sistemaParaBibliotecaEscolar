package com.softwareLibrary.biblioteca.Controller;

import com.softwareLibrary.biblioteca.Entidade.Emprestimo;
import com.softwareLibrary.biblioteca.Service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarEmprestimo(@RequestBody Map<String, String> request) {
        try {
            String matriculaAluno = request.get("matriculaAluno");
            String isbnLivro = request.get("isbnLivro");

            if (matriculaAluno == null || isbnLivro == null) {
                return ResponseEntity.badRequest().body("Matrícula do aluno e ISBN do livro são obrigatórios");
            }

            Emprestimo emprestimo = emprestimoService.realizarEmprestimo(matriculaAluno, isbnLivro);
            return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<?> devolverLivro(@PathVariable Long id) {
        try {
            Emprestimo emprestimo = emprestimoService.devolverLivro(id);
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao devolver livro: " + e.getMessage());
        }
    }

    @PutMapping("/devolver-por-isbn/{isbn}")
    public ResponseEntity<?> devolverLivroPorIsbn(@PathVariable String isbn) {
        try {
            Emprestimo emprestimo = emprestimoService.devolverLivroPorIsbn(isbn);
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao devolver livro: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        List<Emprestimo> emprestimos = emprestimoService.listarTodos();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Emprestimo>> listarAtivos() {
        List<Emprestimo> ativos = emprestimoService.listarAtivos();
        return ResponseEntity.ok(ativos);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<Emprestimo>> listarAtrasados() {
        List<Emprestimo> atrasados = emprestimoService.listarAtrasados();
        return ResponseEntity.ok(atrasados);
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<Emprestimo>> historicoAluno(@PathVariable String matricula) {
        List<Emprestimo> historico = emprestimoService.historicoPorAluno(matricula);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/livro/{isbn}")
    public ResponseEntity<List<Emprestimo>> historicoLivro(@PathVariable String isbn) {
        List<Emprestimo> historico = emprestimoService.historicoPorLivro(isbn);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);
        return emprestimo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/aluno/{matricula}/ativo")
    public ResponseEntity<Emprestimo> buscarEmprestimoAtivo(@PathVariable String matricula) {
        Optional<Emprestimo> emprestimo = emprestimoService.buscarEmprestimoAtivoPorAluno(matricula);
        return emprestimo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}