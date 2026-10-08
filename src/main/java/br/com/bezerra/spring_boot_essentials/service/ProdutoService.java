package br.com.bezerra.spring_boot_essentials.service;

import br.com.bezerra.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.bezerra.spring_boot_essentials.database.repository.ProdutoRepository;
import br.com.bezerra.spring_boot_essentials.exceptions.ProdutoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoEntity salvar(ProdutoEntity produto) {
        return produtoRepository.save(produto);
    }

    public List<ProdutoEntity> findAll() {
        return produtoRepository.findAll();
    }

    public Page<ProdutoEntity> findAllPaginado(
        Pageable pageable){

        return produtoRepository.findAll(pageable);
    }

    public ProdutoEntity findById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(id)
                );
    }

    public void deleteById(Integer id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoEntity atualizar(
            ProdutoEntity produtoExistente,
            ProdutoEntity produtoAtualizado) {

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());

        return produtoRepository.save(produtoExistente);
    }

    public List<ProdutoEntity> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<ProdutoEntity> buscarPorFaixaDePreco(
            BigDecimal min,
            BigDecimal max) {

        if (min.compareTo(max) > 0){
            throw new IllegalArgumentException(
                    "O preço mínimo não pode ser maior que o máximo"
            );
        }

        return produtoRepository.findByPrecoBetween(min, max);
    }
}