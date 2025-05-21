package com.toystore.product.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.toystore.product.application.dto.ProdutoDTO;
import com.toystore.product.domain.exceptions.RecursoNaoEncontradoException;
import com.toystore.product.domain.model.Produto;
import com.toystore.product.domain.repository.ProdutoRepository;
import com.toystore.product.interfaces.mapper.ProdutoMapper;

import static com.toystore.product.infrastructure.utils.ProdutoHelper.criarProduto;
import static com.toystore.product.infrastructure.utils.ProdutoHelper.criarProdutoDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoServiceImpl produtoServiceImpl;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        this.produto = criarProduto();
        this.produtoDTO = criarProdutoDto(produto);
    }

    @DisplayName("Buscar Produto")
    @Nested
    class BuscarProduto {

        @DisplayName("Deve buscar um produto pelo ID fornecido")
        @Test
        void deveBuscarProdutoPorId() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
            when(produtoMapper.toDto(produto)).thenReturn(produtoDTO);

            // Act
            ProdutoDTO resultado = produtoServiceImpl.buscarPorId(produtoId);

            // Assert
            assertNotNull(resultado);
            assertEquals(produtoDTO, resultado);
        }

        @DisplayName("Deve lançar exceção quando o produto não for encontrado")
        @Test
        void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RecursoNaoEncontradoException.class, () -> {
                produtoServiceImpl.buscarPorId(produtoId);
            });
        }

        @DisplayName("Deve buscar todos os produtos")
        @Test
        void deveBuscarTodosOsProdutos() {
            // Arrange
            when(produtoRepository.findAll()).thenReturn(List.of(produto));
            when(produtoMapper.toDto(produto)).thenReturn(produtoDTO);

            // Act
            List<ProdutoDTO> resultado = produtoServiceImpl.buscarTodos();


            // uso do AssertThat
            assertThat(resultado)
                    .isNotNull()
                    .hasSize(1)
                    .contains(produtoDTO);
            // Assert
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(produtoDTO, resultado.get(0));
        }
    }

    @DisplayName("Salvar Produto")
    @Nested
    class SalvarProduto {

        @DisplayName("Deve salvar um novo produto")
        @Test
        void deveSalvarNovoProduto() {
            // Arrange
            when(produtoMapper.toEntity(produtoDTO)).thenReturn(produto);
            when(produtoRepository.findById(produtoDTO.produtoId())).thenReturn(Optional.of(produto)); // Configurar mock
            when(produtoRepository.save(produto)).thenReturn(produto);
            when(produtoMapper.toDto(produto)).thenReturn(produtoDTO);

            // Act
            ProdutoDTO resultado = produtoServiceImpl.salvar(produtoDTO);

            // Assert
            assertNotNull(resultado);
            assertEquals(produtoDTO, resultado);
        }

        @DisplayName("Deve lançar exceção quando o produto não for encontrado")
        @Test
        void deveLancarExcecao_QuandoProduto_NaoEncontrado() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RecursoNaoEncontradoException.class, () -> {
                produtoServiceImpl.salvar(produtoDTO);
            });
        }
            
    }

    @DisplayName("Atualizar Produto")
    @Nested
    class AtualizarProduto {

        @DisplayName("Deve atualizar um produto existente")
        @Test
        void deveAtualizarProdutoExistente() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
            when(produtoRepository.save(produto)).thenReturn(produto);
            when(produtoMapper.toDto(produto)).thenReturn(produtoDTO);

            // Act
            ProdutoDTO resultado = produtoServiceImpl.atualizar(produtoId, produtoDTO);

            // Assert
            assertNotNull(resultado);
            assertEquals(produtoDTO, resultado);
        }
        
        @DisplayName("Deve lançar exceção quando o produto não for encontrado")
        @Test
        void deveLancarExcecao_QuandoProdutoNaoEncontrado() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RecursoNaoEncontradoException.class, () -> {
                produtoServiceImpl.atualizar(produtoId, produtoDTO);
            });
        }
    }

    @DisplayName("Deletar Produto")
    @Nested
    class DeletarProduto {

        @DisplayName("Deve deletar um produto existente")
        @Test
        void deveDeletarProdutoExistente() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.of(produto))
                .thenReturn(Optional.empty()); // Após exclusão, retorna vazio

            // Act
            produtoServiceImpl.deletarPorId(produtoId);

            // Assert
            assertThrows(RecursoNaoEncontradoException.class, () -> {
                produtoServiceImpl.buscarPorId(produtoId);
            });
        }

        @DisplayName("Deve lançar exceção quando o produto não for encontrado")
        @Test
        void deveLancarExcecao_QuandoProdutoNaoEncontrado() {
            // Arrange
            UUID produtoId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
            when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RecursoNaoEncontradoException.class, () -> {
                produtoServiceImpl.deletarPorId(produtoId);
            });
        }
    }
}