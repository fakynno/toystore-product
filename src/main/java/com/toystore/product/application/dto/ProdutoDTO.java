package com.toystore.product.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = "1")
    Long id,

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Min(value = 3, message = "O nome do produto deve ter no mínimo 3 caracteres.")
    @Schema(example = "Produto 1")
    String nome,

    @NotBlank(message = "A marca do produto é obrigatória.")
    @Min(value = 3, message = "A marca do produto deve ter no mínimo 3 caracteres.")
    @Schema(example = "Marca 1")
    String marca,

    @NotBlank(message = "A cor do produto é obrigatória.")
    @Min(value = 4, message = "A cor do produto deve ter no mínimo 4 caracteres.")
    @Schema(example = "Azul")
    String cor,

    @NotBlank(message = "O tamanho do produto é obrigatório.")
    @Min(value = 1, message = "O tamanho do produto deve ter no mínimo 1 caractere.")
    @Schema(example = "M")
    String tamanho,

    @NotBlank(message = "A faixa etária do produto é obrigatória.")
    @Min(value = 1, message = "A faixa etária do produto deve ter no mínimo 1 caractere.")
    @Schema(example = "3 anos")
    String faixaEtaria,

    @Min(value = 2, message = "A quantidade de peças do produto deve ser maior do que 2.")
    @Schema(example = "10")
    int quantidadePecas,

    @NotBlank(message = "O preço do produto é obrigatório.")
    @Min(value = 1, message = "O preço do produto deve ser maior do que 1.")
    @Schema(example = "99.99")
    String preco
) {
}
