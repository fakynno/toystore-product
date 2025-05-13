package com.toystore.product.application.usecases;

import com.toystore.product.application.dto.ProdutoDTO;
import java.util.List;

public interface ProdutoService {

    ProdutoDTO buscarPorId(Long id);
    List<ProdutoDTO> buscarTodos();
    ProdutoDTO salvar(ProdutoDTO produtoDto);
    ProdutoDTO atualizar(Long id, ProdutoDTO produtoDto);
    void deletarPorId(Long id);
}
