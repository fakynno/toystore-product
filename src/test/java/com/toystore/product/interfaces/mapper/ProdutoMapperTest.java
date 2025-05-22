package com.toystore.product.interfaces.mapper;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.model.Produto;
import com.toystore.product.infrastructure.utils.ProdutoHelper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    private final ProdutoMapper mapper = Mappers.getMapper(ProdutoMapper.class);

    @Test
    void testToEntity() {
        ProdutoDTO dto = ProdutoHelper.criarProdutoDto(ProdutoHelper.criarProduto());

        Produto entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.produtoId(), entity.getId());
        assertEquals(dto.skuDoProduto(), entity.getSku());
        assertEquals(dto.nome(), entity.getNome());
        assertEquals(dto.marca(), entity.getMarca());
        assertEquals(dto.cor(), entity.getCor());
        assertEquals(dto.tamanho(), entity.getTamanho());
        assertEquals(dto.faixaEtaria(), entity.getFaixaEtaria());
        assertEquals(dto.quantidadePecas(), entity.getQuantidadePecas());
        assertEquals(dto.preco(), entity.getPreco());

    }

    @Test
    void testToDto() {
        Produto entity = new Produto();
        entity.setId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        entity.setSku("SKU123");

        ProdutoDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.produtoId());
        assertEquals(entity.getSku(), dto.skuDoProduto());
    }

    @Test
    void testUpdateFromDto() {
            ProdutoDTO dto = ProdutoHelper.criarProdutoDto(ProdutoHelper.criarProduto());
            Produto entity = new Produto();
            entity.setId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
            entity.setSku("SKU123");

            mapper.updateFromDto(dto, entity);

            assertNotNull(entity);
            assertEquals(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"), entity.getId()); // ID deve permanecer inalterado
            assertEquals(dto.skuDoProduto(), entity.getSku());
        }
}