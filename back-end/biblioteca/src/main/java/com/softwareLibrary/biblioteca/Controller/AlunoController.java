package com.softwareLibrary.biblioteca.Controller;

import com.softwareLibrary.biblioteca.Entidade.Aluno;
import com.softwareLibrary.biblioteca.Service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody Aluno aluno) {
        try {
            // Validação básica da matrícula
            if (aluno.getMatricula() == null || !aluno.getMatricula().matches("\\d{10}")) {
                return ResponseEntity.badRequest()
                        .body("Matrícula deve conter exatamente 10 dígitos numéricos");
            }

            // Verifica se a matrícula já existe
            if (alunoService.existePorMatricula(aluno.getMatricula())) {
                return ResponseEntity.badRequest()
                        .body("Matrícula já cadastrada: " + aluno.getMatricula());
            }

            Aluno alunoSalvo = alunoService.salvar(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao criar aluno: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Aluno> buscarAluno(@PathVariable String matricula) {
        Optional<Aluno> aluno = alunoService.buscarPorMatricula(matricula);
        return aluno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<?> atualizarAluno(@PathVariable String matricula, @RequestBody Aluno aluno) {
        try {
            if (!alunoService.existePorMatricula(matricula)) {
                return ResponseEntity.notFound().build();
            }
            aluno.setMatricula(matricula); // Garante que a matrícula não seja alterada
            Aluno alunoAtualizado = alunoService.salvar(aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> removerAluno(@PathVariable String matricula) {
        if (!alunoService.existePorMatricula(matricula)) {
            return ResponseEntity.notFound().build();
        }
        alunoService.removerPorMatricula(matricula);
        return ResponseEntity.noContent().build();
    }
}
