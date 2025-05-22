package com.toystore.product.domain.exceptions;

import com.toystore.product.application.usecases.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProdutoControllerTestStub {

    private final ProdutoService produtoService;

    public ProdutoControllerTestStub(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/produtos/{id}")
    public void buscarPorId(@PathVariable UUID id) {
        produtoService.buscarPorId(id);
    }

    @GetMapping("/recurso-ja-salvo")
    public void recursoJaSalvo() {
        throw new RecursoJaSalvoException("Recurso já salvo");
    }
}
