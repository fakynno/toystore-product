package com.toystore.product.interfaces.controller;

import com.toystore.product.application.dto.ProdutoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/produtos")
public interface ProdutoController {

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Produto por ID", description = "Busca um Produto pelo seu ID")
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ProdutoDTO.class)))
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id);
}
