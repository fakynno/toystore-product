package com.toystore.product.domain.model;

import com.toystore.product.infrastructure.utils.SkuGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sku;

    private String nome;
    private String marca;
    private String cor;
    private String tamanho;
    private String faixaEtaria;
    private int quantidadePecas; // ex: quebra-cabeças, Lego etc
    private BigDecimal preco;

    // Gerar o SKU automaticamente
    @PrePersist
    public void gerarSku() {
        this.sku = SkuGenerator.gerar(
                this.marca,
                this.nome,
                this.cor,
                this.tamanho,
                this.faixaEtaria
        );
    }
}
