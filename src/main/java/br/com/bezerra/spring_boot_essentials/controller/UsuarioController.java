package br.com.bezerra.spring_boot_essentials.controller;

import br.com.bezerra.spring_boot_essentials.database.model.UsuarioEntity;
import br.com.bezerra.spring_boot_essentials.dto.ProdutoResponseDTO;
import br.com.bezerra.spring_boot_essentials.dto.UsuarioRequestDTO;
import br.com.bezerra.spring_boot_essentials.dto.UsuarioResponseDTO;
import br.com.bezerra.spring_boot_essentials.mapper.UsuarioMapper;
import br.com.bezerra.spring_boot_essentials.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public final UsuarioService usuarioService;
    public final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO salvar(
            @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

        UsuarioEntity usuario =
                usuarioMapper.toEntity(usuarioDTO);

        UsuarioEntity usuarioSalvo =
                usuarioService.salvar(usuario);

        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDTO> findAll() {

        List<UsuarioEntity> usuarios =
                usuarioService.findAll();

        return usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UsuarioResponseDTO> findById(
            @PathVariable Integer id) {

        UsuarioEntity usuario =
                usuarioService.findById(id);

        UsuarioResponseDTO response =
                usuarioMapper.toResponseDTO(usuario);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

        UsuarioEntity usuarioExistente =
                usuarioService.findById(id);

        UsuarioEntity novosDados =
                usuarioMapper.toEntity(usuarioDTO);

        UsuarioEntity usuarioAtualizado =
                usuarioService.atualizar(
                        usuarioExistente,
                        novosDados
                );

        UsuarioResponseDTO response =
                usuarioMapper.toResponseDTO(usuarioAtualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Integer id) {

        usuarioService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
