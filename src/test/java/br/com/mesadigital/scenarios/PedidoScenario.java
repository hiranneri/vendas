package br.com.mesadigital.scenarios;

import br.com.mesadigital.vendas.controller.dto.FormaPagamentoEnum;
import br.com.mesadigital.vendas.controller.dto.ItemDTO;
import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import br.com.mesadigital.vendas.controller.dto.ProdutoDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoScenario {

    private static PedidoDTO pedidoDTO = new PedidoDTO();

    public static ProdutoDTO getProdutoCompleto() {
        return new ProdutoDTO(
                1L,"789987000",true,10,"Sadia","Pizza",new BigDecimal("15"),
                new BigDecimal("10"),"10/01/2027", true
        );
    }

    public static PedidoDTO getPedidoCompleto() {
        pedidoDTO.setCaixaId(1L);
        pedidoDTO.setDataHora(LocalDateTime.now());
        pedidoDTO.setTroco(new BigDecimal("50"));
        pedidoDTO.setNomeUsuario("Alex");
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setTotal(new BigDecimal("500"));
        pedidoDTO.setItens(
                //Long idProduto, String nome, float quantidade, float valorTotalItem
                List.of(
                        new ItemDTO(
                            1L,"Pizza", new BigInteger("5") ,BigDecimal.TEN
                        ),
                        new ItemDTO(1L,"Pizza",new BigInteger("5"),BigDecimal.TEN)
                )
        );
        pedidoDTO.setFormasPagamento(List.of(FormaPagamentoEnum.DEBITO));
        return pedidoDTO;
    }

    public static PedidoDTO getPedidoComItemSemTotal() {
        pedidoDTO.setCaixaId(1L);
        pedidoDTO.setDataHora(LocalDateTime.now());
        pedidoDTO.setTroco(new BigDecimal("50"));
        pedidoDTO.setNomeUsuario("Alex");
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setTotal(null);
        pedidoDTO.setTotal(new BigDecimal("500"));
        pedidoDTO.setItens(
                //Long idProduto, String nome, float quantidade, float valorTotalItem
                List.of(
                        new ItemDTO( 1L,"Pizza",new BigInteger("5")),
                        new ItemDTO(1L,"Pizza",new BigInteger("5"))
                )
        );
        pedidoDTO.setFormasPagamento(List.of(FormaPagamentoEnum.DEBITO));
        return pedidoDTO;
    }

    public static PedidoDTO getPedidoCompletoSemUsuario() {
        pedidoDTO.setCaixaId(1L);
        pedidoDTO.setDataHora(LocalDateTime.now());
        pedidoDTO.setTroco(new BigDecimal("50"));
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setTotal(new BigDecimal("500"));
        pedidoDTO.setNomeUsuario(null);
        pedidoDTO.setItens(
                //Long idProduto, String nome, float quantidade, float valorTotalItem
                List.of(
                        new ItemDTO( 1L,"Pizza",new BigInteger("5")),
                        new ItemDTO(1L,"Pizza",new BigInteger("5"))
                )
        );
        pedidoDTO.setFormasPagamento(List.of(FormaPagamentoEnum.DEBITO));
        return pedidoDTO;
    }

    public static PedidoDTO getPedidoCompletoSemDataHora() {
        pedidoDTO.setCaixaId(1L);
        pedidoDTO.setTroco(new BigDecimal("50"));
        pedidoDTO.setDataHora(null);
        pedidoDTO.setNomeUsuario("Alex");
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setTotal(new BigDecimal("500"));
        pedidoDTO.setItens(
                //Long idProduto, String nome, float quantidade, float valorTotalItem
                List.of(
                        new ItemDTO( 1L,"Pizza",new BigInteger("5")),
                        new ItemDTO(1L,"Pizza",new BigInteger("5"))
                )
        );
        pedidoDTO.setFormasPagamento(List.of(FormaPagamentoEnum.DEBITO));
        return pedidoDTO;
    }
}
