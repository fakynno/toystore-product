package com.toystore.product.interfaces.controller;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.application.usecases.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProdutoControllerImpl implements ProdutoController {

    private final ProdutoService produtoService;

    @Override
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long idProduto) {
        return ResponseEntity.ok(produtoService.buscarPorId(idProduto));
    }

    @Override
    public ResponseEntity<List<ProdutoDTO>> buscarTodos() {
        return ResponseEntity.ok(produtoService.buscarTodos());
    }

    @Override
    public ResponseEntity<ProdutoDTO> salvar(@RequestBody ProdutoDTO produtoDto) {
        ProdutoDTO produtoCriado = produtoService.salvar(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @Override
    public ResponseEntity<ProdutoDTO> atualizar(Long idProduto, ProdutoDTO produtoDto) {
        produtoDto = produtoService.atualizar(idProduto, produtoDto);
        return ResponseEntity.ok(produtoDto);
    }

    @Override
    public ResponseEntity<Void> deletarPorId(Long idProduto) {
        produtoService.deletarPorId(idProduto);
        return ResponseEntity.noContent().build();
    }
}
