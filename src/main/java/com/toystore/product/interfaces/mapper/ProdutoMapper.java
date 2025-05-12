package com.toystore.product.interfaces.mapper;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface ProdutoMapper {

    @Mapping(source = "produtoId", target = "id")
    @Mapping(source = "skuDoProduto", target = "sku")
    Produto toEntity(ProdutoDTO dto);

    @Mapping(source = "id", target = "produtoId")
    @Mapping(source = "sku", target = "skuDoProduto")
    ProdutoDTO toDto(Produto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "skuDoProduto", target = "sku")
    void updateFromDto(ProdutoDTO dto, @MappingTarget Produto entity);
}
