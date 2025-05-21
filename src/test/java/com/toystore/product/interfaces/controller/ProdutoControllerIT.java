package com.toystore.product.interfaces.controller;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.application.usecases.ProdutoServiceImpl;
import com.toystore.product.infrastructure.utils.SkuGenerator;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = {"/clean.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProdutoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoServiceImpl produtoService;

    private static RequestSpecification requestSpec;

    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @DisplayName( "Buscar Produto" )
    @Nested
    class BuscarProdutoPorId {

        @DisplayName( "Deve retornar 200 quando o produto existe" )
        @Test
        void deveRetornar200_QuandoProdutoExistir() {
            // Arrange
            ProdutoDTO produtoSalvo = produtoService.salvar(new ProdutoDTO(
                    null, "Produto Teste", "Marca Teste", "Azul", "M", "Adulto", 10, BigDecimal.valueOf(99.99), null
            ));

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .get("/produtos/{idProduto}", produtoSalvo.produtoId())
                    .then()
                    .statusCode(200)
                    .body("produtoId", equalTo(produtoSalvo.produtoId().toString()))
                    .body("nome", equalTo("Produto Teste"))
                    .body("marca", equalTo("Marca Teste"));
        }

        @DisplayName( "Deve retornar 404 quando o produto não existe" )
        @Test
        void deveRetornar404_QuandoProdutoNaoExistir() {
            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .get("/produtos/{idProduto}", UUID.randomUUID())
                    .then()
                    .statusCode(404);
        }
    }

    @DisplayName("Buscar todos os produtos")
    @Nested
    class BuscarTodosOsProdutos {

        @DisplayName("Deve retornar 200 quando existem produtos")
        @Test
        void deveRetornar200_QuandoExistemProdutos() {
            // Arrange
            produtoService.salvar(new ProdutoDTO(
                    null, "Produto Teste", "Marca Teste", "Azul", "M", "Adulto", 10, BigDecimal.valueOf(99.99), null
            ));

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .get("/produtos")
                    .then()
                    .statusCode(200);
        }

        @DisplayName("Deve retornar 200 com uma lista de produtos")
        @Test
        void deveRetornar200_QuandoExistemVariosProdutos() {
            // Arrange
            produtoService.salvar(new ProdutoDTO(
                    null, "Produto Teste", "Marca Teste", "Azul", "M", "Adulto", 10, BigDecimal.valueOf(99.99), SkuGenerator.gerar("Marca Teste", "Produto Teste", "Azul", "M", "Adulto")
            ));
            produtoService.salvar(new ProdutoDTO(
                    null, "Produto 1", "Marca 1", "Vermelho", "P", "Infantil", 5, BigDecimal.valueOf(49.99), SkuGenerator.gerar("Teste Marca", "Produto 1", "Vermelho", "P", "Infantil")
            ));
            produtoService.salvar(new ProdutoDTO(
                    null, "Produto 2", "Marca 2", "Azul", "M", "Adulto", 10, BigDecimal.valueOf(99.99), SkuGenerator.gerar("Bandai Marca 2", "Produto 2", "Azul", "M", "Adulto")
            ));

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .get("/produtos")
                    .then()
                    .statusCode(200)
                    .body("$.size()", greaterThan(0));
        }

        @DisplayName("Deve retornar lista vazia quando não houver produto")
        @Test
        void deveRetornarListaVazia_QuandoNaoExistiremProdutos() {
            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .get("/produtos")
                    .then()
                    .statusCode(200)
                    .body("$.size()", equalTo(0));
        }
    }

    @DisplayName("Salvar um produto")
    @Nested
    class SalvarProduto {

        @DisplayName("Deve retornar 201 quando o produto for salvo com sucesso")
        @Test
        void deveSalvarProduto_ComSucesso() {
            // Arrange
            ProdutoDTO novoProduto = new ProdutoDTO(
                    null, "Produto Novo", "Marca Nova", "Preto", "G", "Adulto", 15, BigDecimal.valueOf(199.99), null
            );

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .body(novoProduto)
                    .when()
                    .post("/produtos")
                    .then()
                    .statusCode(201)
                    .body("nome", equalTo("Produto Novo"))
                    .body("marca", equalTo("Marca Nova"))
                    .body("cor", equalTo("Preto"))
                    .body("tamanho", equalTo("G"))
                    .body("faixaEtaria", equalTo("Adulto"))
                    .body("quantidadePecas", equalTo(15))
                    .body("preco", equalTo(199.99f));
        }

        @DisplayName("Deve retornar 400 quando o produto não for salvo com dados inválidos")
        @Test
        void deveRetornar400_QuandoDadosInvalidos() {
            // Arrange
            ProdutoDTO produtoInvalido = new ProdutoDTO(
                    null, "", "Marca Invalida", null, "G", "Adulto", -5, null, null
            );

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .body(produtoInvalido)
                    .when()
                    .post("/produtos")
                    .then()
                    .statusCode(400);
        }

    }

    @DisplayName("Atualizar um produto")
    @Nested
    class AtualizarProduto {

        @DisplayName("Deve retornar 200 quando o produto for atualizado com sucesso")
        @Test
        void deveAtualizarProduto_ComSucesso() {
            // Arrange
            ProdutoDTO produtoSalvo = produtoService.salvar(new ProdutoDTO(
                    null, "Produto Antigo", "Marca Antiga", "Branco", "M", "Adulto", 5, BigDecimal.valueOf(49.99), null
            ));

            ProdutoDTO produtoAtualizado = new ProdutoDTO(
                    null, "Produto Atualizado", "Marca Atualizada", "Preto", "G", "Infantil", 10, BigDecimal.valueOf(99.99), null
            );

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .body(produtoAtualizado)
                    .when()
                    .put("/produtos/{idProduto}", produtoSalvo.produtoId())
                    .then()
                    .statusCode(200)
                    .body("nome", equalTo("Produto Atualizado"))
                    .body("marca", equalTo("Marca Atualizada"))
                    .body("cor", equalTo("Preto"))
                    .body("tamanho", equalTo("G"))
                    .body("faixaEtaria", equalTo("Infantil"))
                    .body("quantidadePecas", equalTo(10))
                    .body("preco", equalTo(99.99f));
        }

        @DisplayName("Deve retornar 404 quando o produto não existir")
        @Test
        void deveRetornar404_QuandoProdutoNaoExistir() {
            // Arrange
            ProdutoDTO produtoAtualizado = new ProdutoDTO(
                    null, "Produto Atualizado", "Marca Atualizada", "Preto", "G", "Infantil", 10, BigDecimal.valueOf(99.99), null
            );

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .body(produtoAtualizado)
                    .when()
                    .put("/produtos/{idProduto}", UUID.randomUUID())
                    .then()
                    .statusCode(404);
        }

        @DisplayName("Deve retornar 400 quando os dados fornecidos forem inválidos")
        @Test
        void deveRetornar400_QuandoDadosInvalidos() {
            // Arrange
            ProdutoDTO produtoSalvo = produtoService.salvar(new ProdutoDTO(
                    null, "Produto Antigo", "Marca Antiga", "Branco", "M", "Adulto", 5, BigDecimal.valueOf(49.99), null
            ));

            ProdutoDTO produtoInvalido = new ProdutoDTO(
                    null, "", "", null, "G", "Infantil", -10, null, null
            );

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .body(produtoInvalido)
                    .when()
                    .put("/produtos/{idProduto}", produtoSalvo.produtoId())
                    .then()
                    .statusCode(400);
        }
    }

    @DisplayName("Deletar um produto")
    @Nested
    class DeletarProduto {

        @DisplayName("Deve retornar 400 quando o produto for deletado com sucesso")
        @Test
        void deveDeletarProduto_ComSucesso() {
            // Arrange
            ProdutoDTO produtoSalvo = produtoService.salvar(new ProdutoDTO(
                    null, "Produto Teste", "Marca Teste", "Azul", "M", "Adulto", 10, BigDecimal.valueOf(99.99), null
            ));

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .delete("/produtos/{idProduto}", produtoSalvo.produtoId())
                    .then()
                    .statusCode(204);
        }

        @DisplayName("Deve retornar 404 quando o produto não existir")
        @Test
        void deveRetornar404_QuandoProdutoNaoExistir() {
            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .when()
                    .delete("/produtos/{idProduto}", UUID.randomUUID())
                    .then()
                    .statusCode(404);
        }
    }

    @DisplayName("Salvar produto duplicado")
    @Nested
    class SalvarProdutoDuplicado {

        @DisplayName("Deve retornar 409 quando tentar salvar um produto já existente")
        @Test
        void deveRetornar409_QuandoProdutoJaExistir() {
            // Arrange
            ProdutoDTO produtoDuplicado = new ProdutoDTO(
                    null, "Produto Duplicado", "Marca Duplicada", "Azul", "M", "Adulto", 10, BigDecimal.valueOf(99.99), null
            );

            produtoService.salvar(produtoDuplicado);

            // Act & Assert
            RestAssured.given()
                    .spec(requestSpec)
                    .body(produtoDuplicado)
                    .when()
                    .post("/produtos")
                    .then()
                    .statusCode(409)
                    .body("message", equalTo("Recurso já salvo"))
                    .body("status", equalTo(409));
        }
    }

}