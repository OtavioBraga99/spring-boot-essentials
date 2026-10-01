package br.com.bezerra.spring_boot_essentials.controller;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.ProdutoRepository;
import br.com.bezerra.spring_boot_essentials.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity salvar(@RequestBody ProdutoEntity produto) {
        return produtoService.salvar(produto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoEntity> findAll(){
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProdutoEntity> findById(@PathVariable Integer id){
        return produtoService.findById(id);
    }
}

