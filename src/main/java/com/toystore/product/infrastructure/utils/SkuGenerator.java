package com.toystore.product.infrastructure.utils;

public class SkuGenerator {

    public static String gerar(String marca, String nome, String cor, String tamanho, String faixaEtaria) {

        // Utiliza abreviação para pegar as 3 primeiras letras em maiúsculo
        String marcaAbreviada = marca != null ? marca.substring(0, Math.min(3, marca.length())).toUpperCase() : "GEN";
        String nomeAbreviado = nome != null ? nome.substring(0, Math.min(3, nome.length())).toUpperCase() : "BRIN";
        String corAbreviada = cor != null ? cor.substring(0, Math.min(3, cor.length())).toUpperCase() : "COR";
        String tamanhoAbreviado = tamanho != null ? tamanho.substring(0, 1).toUpperCase() : "U"; // U para Unissex, P para Pequeno, M para Médio, G para Grande
        String faixaEtariaAbreviada = faixaEtaria != null ? faixaEtaria.replace("+", "") : "0"; // 0 para todas as idades, 1 para 0-3 anos, 2 para 4-6 anos, etc.

        return String.format("%s-%s-%s-%s-%s",
                marcaAbreviada,
                nomeAbreviado,
                corAbreviada,
                tamanhoAbreviado,
                faixaEtariaAbreviada);
    }
}
