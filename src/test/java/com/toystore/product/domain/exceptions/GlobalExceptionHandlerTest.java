package com.toystore.product.domain.exceptions;

import com.toystore.product.application.usecases.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {GlobalExceptionHandler.class, ProdutoControllerTestStub.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Test
    void testHandleRecursoNaoEncontradoException() throws Exception {
        UUID id = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

        when(produtoService.buscarPorId(id))
                .thenThrow(new RecursoNaoEncontradoException("Recurso não encontrado"));

        mockMvc.perform(get("/produtos/" + id) // Simula uma rota que lança a exceção
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void testHandleRecursoJaSalvoException() throws Exception {
        mockMvc.perform(get("/recurso-ja-salvo") // Simula uma rota que lança a exceção
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Recurso já salvo"))
                .andExpect(jsonPath("$.status").value(409));
    }

}