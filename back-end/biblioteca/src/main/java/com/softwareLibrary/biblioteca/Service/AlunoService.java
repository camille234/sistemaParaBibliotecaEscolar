package com.softwareLibrary.biblioteca.Service;

import com.softwareLibrary.biblioteca.Entidade.Aluno;
import com.softwareLibrary.biblioteca.Repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorMatricula(String matricula) {
        return alunoRepository.findById(matricula); // Agora aceita String
    }

    public void removerPorMatricula(String matricula) {
        alunoRepository.deleteById(matricula); // Agora aceita String
    }

    public boolean existePorMatricula(String matricula) {
        return alunoRepository.existsByMatricula(matricula);
    }
}
