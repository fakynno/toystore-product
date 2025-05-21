package com.toystore.product.interfaces.controller;

import com.toystore.product.application.dto.ProdutoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Recurso para Gestão de Produtos")
public interface ProdutoController {

    @GetMapping("/{idProduto}")
    @Operation(summary = "Busca Produto por ID", description = "Busca um Produto pelo seu ID")
        @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
    ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable UUID idProduto);

    @GetMapping
    @Operation(summary = "Busca todos os Produtos", description = "Efetua a busca de todos os Produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos encontrados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<List<ProdutoDTO>> buscarTodos();

    @PostMapping
    @Operation(summary = "Salva um Produto", description = "Salva o registro de um Produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum Produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<ProdutoDTO> salvar(ProdutoDTO produtoDto);

    @PutMapping("/{idProduto}")
    @Operation(summary = "Atualiza um Produto", description = "Atualiza o registro de um Produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<ProdutoDTO> atualizar(@PathVariable UUID idProduto, @RequestBody ProdutoDTO produtoDto);

    @DeleteMapping("/{idProduto}")
    @Operation(summary = "Deleta um Produto", description = "Deleta o registro de um Produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Void> deletarPorId(@PathVariable UUID idProduto);
}
