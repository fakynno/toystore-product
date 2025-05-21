package com.toystore.product.application.usecases;

import com.toystore.product.application.dto.ProdutoDTO;
import java.util.List;
import java.util.UUID;

public interface ProdutoService {

    ProdutoDTO buscarPorId(UUID id);
    List<ProdutoDTO> buscarTodos();
    ProdutoDTO salvar(ProdutoDTO produtoDto);
    ProdutoDTO atualizar(UUID id, ProdutoDTO produtoDto);
    void deletarPorId(UUID id);
}
