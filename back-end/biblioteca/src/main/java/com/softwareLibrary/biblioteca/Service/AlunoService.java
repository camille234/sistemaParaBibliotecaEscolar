package com.softwareLibrary.biblioteca.Service;

import com.softwareLibrary.biblioteca.Entidade.Aluno;
import com.softwareLibrary.biblioteca.Repository.AlunoRepository;
import com.softwareLibrary.biblioteca.Repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorMatricula(String matricula) {
        return alunoRepository.findById(matricula); // Agora aceita String
    }

    // Verificar se matrícula é válida e retornar aluno
    public Optional<Aluno> autenticarPorMatricula(String matricula) {
        if (matricula == null || !matricula.matches("\\d{10}")) {
            return Optional.empty();
        }
        return buscarPorMatricula(matricula);
    }

    public boolean hasEmprestimoAtivo(String matricula) {
        return emprestimoRepository.existsByMatriculaAlunoAndStatusIn(
                matricula, Arrays.asList("PENDENTE", "ATRASADO"));
    }

    public void removerPorMatricula(String matricula) {
        alunoRepository.deleteById(matricula); // Agora aceita String
    }

    public boolean existePorMatricula(String matricula) {
        return alunoRepository.existsByMatricula(matricula);
    }
}
