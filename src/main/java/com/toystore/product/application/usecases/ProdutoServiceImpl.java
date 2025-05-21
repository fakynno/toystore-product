package com.toystore.product.application.usecases;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.exceptions.RecursoJaSalvoException;
import com.toystore.product.domain.exceptions.RecursoNaoEncontradoException;
import com.toystore.product.domain.model.Produto;
import com.toystore.product.domain.repository.ProdutoRepository;
import com.toystore.product.infrastructure.utils.AtualizaProdutoDtoComSku;
import com.toystore.product.interfaces.mapper.ProdutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Override
    public ProdutoDTO buscarPorId(UUID id) {
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
        if (produtoDto.produtoId() != null && produtoRepository.findById(produtoDto.produtoId()).isEmpty()) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com id: " + produtoDto.produtoId());
        }
        if (produtoRepository.findBySku((produtoDto.skuDoProduto())).isPresent()) {
            throw new RecursoJaSalvoException("Produto já cadastrado com sku: " + produtoDto.skuDoProduto());
        }
        if (produtoRepository.existsByNome((produtoDto.nome()))) {
            throw new RecursoJaSalvoException("Recurso já salvo");
        }
        Produto produto = produtoMapper.toEntity(produtoDto);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDto(produtoSalvo);
    }

    @Override
    public ProdutoDTO atualizar(UUID id, ProdutoDTO produtoDto) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Produto não encontrado com id: " + id)
        );

        ProdutoDTO produtoDtoAtualizado = AtualizaProdutoDtoComSku.getProdutoDTO(produtoDto);

        produtoMapper.updateFromDto(produtoDtoAtualizado, produto);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDto(produto);
    }



    @Override
    public void deletarPorId(UUID id) {
        this.buscarPorId(id);
        produtoRepository.deleteById(id);
    }
}
