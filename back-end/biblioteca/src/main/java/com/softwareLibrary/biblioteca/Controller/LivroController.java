package com.softwareLibrary.biblioteca.Controller;

import com.softwareLibrary.biblioteca.Entidade.Livro;
import com.softwareLibrary.biblioteca.Service.LivroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro salvar(@RequestBody Livro livro){
        return livroService.salvar(livro);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Livro> listaLivro(){
        return livroService.listaLivro();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Livro buscarLivro(@PathVariable("id") Long id){
        return livroService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O livro não foi encontrado! "));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizacaoLivro(@PathVariable("id") Long id, @RequestBody Livro livro){
        livroService.buscarPorId(id)
                .map(livroBase -> {
                    modelMapper.map(livro, livroBase);
                    livroService.salvar(livroBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O livro não foi encontrado!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerLivro(@PathVariable("id") Long id){
        livroService.buscarPorId(id).map(livro -> {
            livroService.removerPorId(livro.getId());
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O livro não foi encontrado!"));
    }

}
