package com.toystore.product.infrastructure.utils;

import java.math.BigDecimal;
import java.util.UUID;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.model.Produto;

public class ProdutoHelper {

    public static Produto criarProduto() {
        return Produto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .nome("Produto Teste")
                .marca("Marca Teste")
                .cor("Vermelho")
                .tamanho("M")
                .faixaEtaria("5")
                .quantidadePecas(100)
                .preco(BigDecimal.valueOf(19.99))
                .sku("SKU-123-456")
                .build();
    }

    public static ProdutoDTO criarProdutoDto(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getMarca(),
                produto.getCor(),
                produto.getTamanho(),
                produto.getFaixaEtaria(),
                produto.getQuantidadePecas(),
                produto.getPreco(),
                produto.getSku()
        );
    }

}
