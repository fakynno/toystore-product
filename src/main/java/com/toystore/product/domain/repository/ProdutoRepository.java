package com.toystore.product.domain.repository;

import com.toystore.product.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Object> findBySku(String sku);

    boolean existsByNome(String nome);
}
