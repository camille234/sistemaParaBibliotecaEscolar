package com.softwareLibrary.biblioteca.Controller;

import com.softwareLibrary.biblioteca.DTO.AlunoLoginRequestDto;
import com.softwareLibrary.biblioteca.DTO.AlunoLoginResponseDto;
import com.softwareLibrary.biblioteca.DTO.LoginRequestDto;
import com.softwareLibrary.biblioteca.DTO.LoginResponseDto;
import com.softwareLibrary.biblioteca.Entidade.Aluno;
import com.softwareLibrary.biblioteca.Service.AlunoService;
import com.softwareLibrary.biblioteca.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AlunoService alunoService;

    // Login da bibliotecária (existente)
    @PostMapping("/bibliotecaria")
    public ResponseEntity<?> loginBibliotecaria(@RequestBody LoginRequestDto loginRequest) {
        try {
            boolean isAuthenticated = authService.authenticate(loginRequest.getSenha());

            if (isAuthenticated) {
                return ResponseEntity.ok().body(new LoginResponseDto("Login realizado com sucesso"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponseDto("Senha incorreta"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponseDto("Erro durante a autenticação: " + e.getMessage()));
        }
    }

    // Novo: Autenticação do aluno por matrícula
    @PostMapping("/aluno")
    public ResponseEntity<?> loginAluno(@RequestBody AlunoLoginRequestDto loginRequest) {
        try {
            // Validação básica da matrícula
            if (loginRequest.getMatricula() == null || !loginRequest.getMatricula().matches("\\d{10}")) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponseDto("Matrícula deve conter exatamente 10 dígitos numéricos"));
            }

            Optional<Aluno> aluno = alunoService.buscarPorMatricula(loginRequest.getMatricula());

            if (aluno.isPresent()) {
                Aluno alunoEncontrado = aluno.get();
                return ResponseEntity.ok().body(new AlunoLoginResponseDto(
                        "Aluno autenticado com sucesso",
                        alunoEncontrado.getMatricula(),
                        alunoEncontrado.getNome(),
                        alunoEncontrado.getTurma()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponseDto("Matrícula não encontrada"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponseDto("Erro durante a autenticação: " + e.getMessage()));
        }
    }
}