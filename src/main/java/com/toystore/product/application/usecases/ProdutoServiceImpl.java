package com.toystore.product.application.usecases;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.exceptions.RecursoNaoEncontradoException;
import com.toystore.product.domain.model.Produto;
import com.toystore.product.domain.repository.ProdutoRepository;
import com.toystore.product.infrastructure.utils.AtualizaProdutoDtoComSku;
import com.toystore.product.interfaces.mapper.ProdutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Override
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Produto não encontrado com id: " + id)
        );
        return produtoMapper.toDto(produto);
    }

    @Override
    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toDto)
                .toList();
    }

    @Override
    public ProdutoDTO salvar(ProdutoDTO produtoDto) {
        Produto produto = produtoMapper.toEntity(produtoDto);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDto(produtoSalvo);
    }

    @Override
    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDto) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Produto não encontrado com id: " + id)
        );

        ProdutoDTO produtoDtoAtualizado = AtualizaProdutoDtoComSku.getProdutoDTO(produtoDto);

        produtoMapper.updateFromDto(produtoDtoAtualizado, produto);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDto(produto);
    }



    @Override
    public void deletarPorId(Long id) {
        this.buscarPorId(id);
        produtoRepository.deleteById(id);
    }
}
