package br.com.mesadigital.pedidos;

import br.com.mesadigital.TestContainerConfig;
import br.com.mesadigital.scenarios.AberturaCaixaDTO;
import br.com.mesadigital.scenarios.PedidoScenario;
import br.com.mesadigital.vendas.controller.dto.FormaPagamentoEnum;
import br.com.mesadigital.vendas.controller.dto.ItemDTO;
import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import br.com.mesadigital.vendas.controller.dto.ProdutoDTO;
import br.com.mesadigital.vendas.utils.DataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class AB_PedidoIT extends TestContainerConfig {

    @Test
    @DisplayName("Realizar abertura de caixa")
    void AA_RealizarAberturaDeCaixaComSucesso() {
        AberturaCaixaDTO aberturaCaixaDTO = new AberturaCaixaDTO(
                DataUtils.toPTBR(LocalDateTime.now().minusDays(1)),
                "Fernando",
                BigDecimal.TEN
        );

        AberturaCaixaDTO response = webClient.post()
                .uri("/caixas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(aberturaCaixaDTO)
                .retrieve()
                .bodyToMono(AberturaCaixaDTO.class)
                .block();

        assert response != null;
        Assertions.assertEquals(response.dataHoraAbertura(), aberturaCaixaDTO.dataHoraAbertura());

    }

    @Test
    @DisplayName("Cria um novo produto")
    void AB_CriarProdutoDeveRetornarProdutoSalvoComStatusAtivo() throws Exception {
        ProdutoDTO produtoDTO = PedidoScenario.getProdutoCompleto();
        mockMvc.perform(
                        post("/produtos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produtoDTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(produtoDTO.nome))
                .andExpect(jsonPath("$.status").value(produtoDTO.status));

    }

    @Test
    @DisplayName("Cria um novo pedido")
    void AC_CriarPedidoDeveRetornarPedidoSalvoComStatusAtivo() throws Exception {
        PedidoDTO pedidoCompleto = PedidoScenario.getPedidoCompleto();
        mockMvc.perform(
                        post("/pedidos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pedidoCompleto))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.total").value(pedidoCompleto.getTotal()));
    }

    @Test
    @DisplayName("Procura por todos os pedidos")
    void AD_PesquisarTodosOsPedidosDeveRetornarOsPedidosSalvosComStatusAtivo() throws Exception {
        mockMvc.perform(
                        get("/pedidos/1")

                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    @DisplayName("Apagar um pedido")
    void AE_ExcluirUmPedidoDeveRetornar204() throws Exception {
        mockMvc.perform(
                delete("/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Tenta criar um pedido sem total")
    void AF_CriarPedidoSemTotalDeveRetornar403() throws Exception {
        PedidoDTO pedidoEditado = new PedidoDTO();
        pedidoEditado.setItens(List.of(new ItemDTO()));
        pedidoEditado.setDataHora(LocalDateTime.now());
        pedidoEditado.setFormasPagamento(List.of(FormaPagamentoEnum.DEBITO));
        pedidoEditado.setTroco(new BigDecimal("0.5"));
        pedidoEditado.setCaixaId(1L);

        mockMvc.perform(
                post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoEditado))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Tenta criar pedido sem usuário")
    void AG_CriarPedidoCompletoSemUsuario() throws Exception {
        mockMvc.perform(
                post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PedidoScenario.getPedidoCompletoSemUsuario()))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Tenta criar pedido sem data e hora")
    void AH_CriarPedidoCompletoSemDataHora() throws Exception {
        mockMvc.perform(
                post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PedidoScenario.getPedidoCompletoSemDataHora()))
        ).andExpect(status().is4xxClientError());
    }


}
