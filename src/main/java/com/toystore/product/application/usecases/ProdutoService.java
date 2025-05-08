package com.toystore.product.application.usecases;

import com.toystore.product.application.dto.ProdutoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoService {

    ProdutoDTO buscarPorId(Long id);
    List<ProdutoDTO> buscarTodos();
    ProdutoDTO salvar(ProdutoDTO produtoDto);
    ProdutoDTO atualizar(Long id, ProdutoDTO produtoDto);
    void deletarPorId(Long id);
}
