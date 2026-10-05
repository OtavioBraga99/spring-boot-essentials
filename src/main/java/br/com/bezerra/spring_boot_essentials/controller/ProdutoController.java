package br.com.bezerra.spring_boot_essentials.controller;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoResponseDTO;
import br.com.bezerra.spring_boot_essentials.mapper.ProdutoMapper;
import br.com.bezerra.spring_boot_essentials.service.ProdutoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ProdutoResponseDTO> findAll() {

        List<ProdutoEntity> produtos =
                produtoService.findAll();

        return produtos.stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(
            @PathVariable Integer id) {

        ProdutoEntity produto =
                produtoService.findById(id);

        ProdutoResponseDTO response =
                produtoMapper.toResponseDTO(produto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public List<ProdutoResponseDTO> buscarPorNome(
            @RequestParam String nome){

        List<ProdutoEntity> produtos =
                produtoService.buscarPorNome(nome);

        return produtos.stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProdutoRequestDTO produtoDTO) {

        ProdutoEntity produtoExistente =
                produtoService.findById(id);

        ProdutoEntity novosDados =
                produtoMapper.toEntity(produtoDTO);

        ProdutoEntity produtoAtualizado =
                produtoService.atualizar(
                        produtoExistente,
                        novosDados
                );

        ProdutoResponseDTO response =
                produtoMapper.toResponseDTO(produtoAtualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Integer id) {

        produtoService.findById(id);

        produtoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

