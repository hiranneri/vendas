package br.com.mesadigital.pedidos;

import br.com.mesadigital.TestContainerConfig;
import br.com.mesadigital.vendas.controller.dto.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class AA_ProdutoIT extends TestContainerConfig {

    public static ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp(){
        produtoDTO = new ProdutoDTO(
                null,
                "789000000",
                false,
                10,
                "Pizza Mussarela",
                "Sadia",
                new BigDecimal("45"),
                new BigDecimal("40"),
                "10/01/2027",
                true
        );
        
    }

    @Test
    @DisplayName("Cria uma nova Produto")
    void AB_CriarProdutoDeveRetornarProdutoSalvoComStatusAtivo() throws Exception {
        mockMvc.perform(
                        post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produtoDTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(produtoDTO.nome))
                .andExpect(jsonPath("$.status").value(produtoDTO.status));

    }

    @Test
    @DisplayName("Procura por todos os produtos")
    void AC_PesquisarPorUmProdutoDeveRetornarOProdutoComStatusAtivo() throws Exception {

        mockMvc.perform(
                        get("/produtos/"+1)

                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap());
    }


    @Test
    @DisplayName("Apagar um produto")
    void AE_ExcluirUmProdutoDeveRetornar204() throws Exception {
        mockMvc.perform(
                delete("/produtos/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Tenta criar um produto sem nome")
    void AF_CriarProdutoSemNomeDeveRetornar403() throws Exception {
        ProdutoDTO novoProduto = new ProdutoDTO(
                null,
                "789000001",
                false,
                10,
                "Sadia",
                "",
                new BigDecimal("45"),
                new BigDecimal("40"),
                "10/01/2027",
                true
        );

        mockMvc.perform(
                post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoProduto))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Tenta procurar um produto excluído")
    void AG_PesquisarProdutoExcluidaDeveRetornar403() throws Exception {

        long idProduto = 1L;
        mockMvc.perform(
                get("/produtos" + idProduto)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }


}
