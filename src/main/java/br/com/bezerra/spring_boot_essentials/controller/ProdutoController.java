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
    public ResponseEntity<ProdutoEntity> findById(@PathVariable Integer id){

        Optional<ProdutoEntity> produto = produtoService.findById(id);
        if (produto.isPresent()){
            return ResponseEntity.ok(produto.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){

        Optional<ProdutoEntity> produto = produtoService.findById(id);

        if (produto.isPresent()){
            produtoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}

