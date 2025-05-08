package com.toystore.product.interfaces.mapper;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "produto.id", source = "produtoId")
    Produto toEntity(ProdutoDTO dto);

    @Mapping(target = "produtoId", source = "produto.id")
    ProdutoDTO toDto(Produto entity);

    @Mapping(target = "produto.id", source = "produtoId")
    @Mapping(target = "id", ignore = true)
    void updateFromDto(ProdutoDTO dto, @MappingTarget Produto entity);
}
