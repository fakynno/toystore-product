package com.toystore.product.interfaces.controller;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.application.usecases.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Produtos", description = "Recurso para Gestão de Produtos")
public class ProdutoControllerImpl implements ProdutoController {

    private final ProdutoService produtoService;

    @Override
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long idProduto) {
        return ResponseEntity.ok(produtoService.buscarPorId(idProduto));
    }
}
