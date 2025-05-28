package br.com.mesadigital.vendas.service;

import br.com.mesadigital.vendas.controller.dto.FormaPagamentoEnum;
import br.com.mesadigital.vendas.controller.dto.ItemDTO;
import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import br.com.mesadigital.vendas.controller.dto.PedidoPutDTO;
import br.com.mesadigital.vendas.model.FormaPagamento;
import br.com.mesadigital.vendas.model.ItemPedido;
import br.com.mesadigital.vendas.model.Pedido;
import br.com.mesadigital.vendas.model.Produto;
import br.com.mesadigital.vendas.repository.FormaPagamentoRepository;
import br.com.mesadigital.vendas.repository.PedidoRepository;
import br.com.mesadigital.vendas.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BuscaPedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    private static final boolean STATUS_ATIVO = true;

    @Transactional
    public Pedido buscarPedidoPeloId(@Valid PedidoPutDTO produtoPutDTO) throws BadRequestException {
        Pedido pedido;
        pedido = pedidoRepository.findById(produtoPutDTO.getId())
                .orElseThrow(() -> new BadRequestException("Pedido não encontrado"));
        return pedido;
    }


    @Transactional
    public List<FormaPagamento> getFormasPagamentoNoPedido(PedidoDTO pedidoDTO) {
        try {
            List<FormaPagamentoEnum> formasPagamentoNoPedido = new ArrayList<>(pedidoDTO.getFormasPagamento());
            List<String> nomesFormasPagamentoNoPedido = new ArrayList<>();

            for (FormaPagamentoEnum forma : formasPagamentoNoPedido) {
                nomesFormasPagamentoNoPedido.add(forma.name());
            }

            List<FormaPagamento> formasPagamentoLocalizadas = formaPagamentoRepository.buscarFormasPagamentoComValores(nomesFormasPagamentoNoPedido);

            if(formasPagamentoLocalizadas.contains(null)) {
                throw new BadRequestException("Alguma ou todas as formas de pagamento não foram localizadas");
            }

            return formasPagamentoLocalizadas;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public Pedido pesquisarVendaPeloIDOuLancarExcecaoNaoEncontrada(Long id) throws BadRequestException {
        Pedido pedido;
        pedido = pedidoRepository.findByIdAndStatus(id, STATUS_ATIVO)
                .orElseThrow(() -> new BadRequestException("Venda não encontrada"));
        return pedido;
    }

    @Transactional
    public Pedido buscarPedidoPeloId(@Valid Long id) throws BadRequestException {
        Pedido pedido;
        pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Pedido não encontrado"));
        return pedido;
    }

    @Transactional
    public List<ItemPedido> buscarItensNoPedido(PedidoDTO pedidoDTO) {
        List<Long> itensIdNoPedido = new ArrayList<>();
        List<ItemPedido> itens = new ArrayList<>();

        for(ItemDTO item: pedidoDTO.getItens()) {
            itensIdNoPedido.add(item.getIdProduto());
        }

        List<Produto> produtos = pesquisarProdutosPeloIDOuLancarExcecaoNaoEncontrada(itensIdNoPedido);

        for(int i=0;i<pedidoDTO.getItens().size();i++) {
            ItemPedido item = new ItemPedido();
            item.setQuantidade(pedidoDTO.getItens().get(i).getQuantidade());
            item.setProduto(produtos.get(i));
            item.setValorTotalItem(pedidoDTO.getItens().get(i).getValorTotalItem());
            itens.add(item);
        }

        return itens;
    }

    @Transactional
    public List<Produto> pesquisarProdutosPeloIDOuLancarExcecaoNaoEncontrada(List<Long> idsInformados) {
        Map<Long, Produto> produtos = produtoRepository.findByIdIn(idsInformados).stream().collect(
                Collectors.toMap(Produto::getId, Function.identity()));

        idsInformados.forEach( idInformado -> {
            if(!produtos.containsKey(idInformado)) {
                throw new RuntimeException("Produto(s) não foram localizado(s)");
            }
        });

        List<Produto> produtosNoPedido = new ArrayList<>(idsInformados.size());

        idsInformados.forEach( id -> produtosNoPedido.add(produtos.get(id)));

        return produtosNoPedido;
    }

}
