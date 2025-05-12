package com.toystore.product.infrastructure.utils;

import com.toystore.product.application.dto.ProdutoDTO;

public class AtualizaProdutoDtoComSku {

    public static ProdutoDTO getProdutoDTO(ProdutoDTO produtoDto) {
        String novoSku = SkuGenerator.gerar(
                produtoDto.marca(),
                produtoDto.nome(),
                produtoDto.cor(),
                produtoDto.tamanho(),
                produtoDto.faixaEtaria()
        );

        return new ProdutoDTO(
                produtoDto.produtoId(),
                produtoDto.nome(),
                produtoDto.marca(),
                produtoDto.cor(),
                produtoDto.tamanho(),
                produtoDto.faixaEtaria(),
                produtoDto.quantidadePecas(),
                produtoDto.preco(),
                novoSku
        );
    }
}
