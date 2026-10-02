package br.com.bezerra.spring_boot_essentials.controller;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.ProdutoRepository;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoResponseDTO;
import br.com.bezerra.spring_boot_essentials.mapper.ProdutoMapper;
import br.com.bezerra.spring_boot_essentials.service.ProdutoService;
import jakarta.validation.Valid;
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

    public final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService,
                             ProdutoMapper produtoMapper) {

        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO salvar(
            @Valid @RequestBody ProdutoRequestDTO produtoDTO) {

        ProdutoEntity produto =
                produtoMapper.toEntity(produtoDTO);

        ProdutoEntity produtoSalvo =
                produtoService.salvar(produto);

        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDTO> findAll(){

        List<ProdutoEntity> produtos =
                produtoService.findAll();

        return produtos.stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable Integer id){

        Optional<ProdutoEntity> produto = produtoService.findById(id);
        if (produto.isPresent()){

            ProdutoResponseDTO response =
                    produtoMapper.toResponseDTO(produto.get());

            return ResponseEntity.ok(response);
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

    @PutMapping("/{id}")
        public ResponseEntity<ProdutoResponseDTO> atualizar(
                @PathVariable Integer id,
                @Valid @RequestBody ProdutoRequestDTO produtoDTO) {

        Optional<ProdutoEntity> produtoExistente =
                produtoService.findById(id);

        if (produtoExistente.isPresent()){

            ProdutoEntity novosDados =
                    produtoMapper.toEntity(produtoDTO);

            ProdutoEntity produtoAtualizado =
                    produtoService.atualizar(
                            produtoExistente.get(),
                            novosDados
                    );

            ProdutoResponseDTO response =
                    produtoMapper.toResponseDTO(produtoAtualizado);

            return ResponseEntity.ok(response);

        }

        return ResponseEntity.notFound().build();
    }
}

