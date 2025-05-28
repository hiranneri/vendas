package br.com.mesadigital.vendas.service;


import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import br.com.mesadigital.vendas.controller.dto.PedidoPutDTO;
import br.com.mesadigital.vendas.model.FormaPagamento;
import br.com.mesadigital.vendas.model.ItemPedido;
import br.com.mesadigital.vendas.model.Pedido;
import br.com.mesadigital.vendas.repository.ItemRepository;
import br.com.mesadigital.vendas.repository.PedidoRepository;
import br.com.mesadigital.vendas.service.dto.Caixa;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    BuscaPedidoService buscaPedidoService;

    @Autowired
    AberturaCaixaService aberturaCaixaService;

    private final KafkaTemplate<String, PedidoDTO> kafkaPedidoTemplate;

    public PedidoService(KafkaTemplate<String, PedidoDTO> kafkaPedidoTemplate) {
        this.kafkaPedidoTemplate = kafkaPedidoTemplate;
    }

    @Transactional
    public PedidoDTO realizar(PedidoDTO pedidoDTO) throws BadRequestException {
        Caixa caixaLocalizado = aberturaCaixaService.verificarSeOCaixaEstaAberto(pedidoDTO);
        pedidoDTO.setCaixaId(caixaLocalizado.idCaixa());

        List<FormaPagamento> formasPagamentoNoPedido = buscaPedidoService.getFormasPagamentoNoPedido(pedidoDTO);
        List<ItemPedido> itensPedido = buscaPedidoService.buscarItensNoPedido(pedidoDTO);

        Pedido novoPedido = criarPedido(pedidoDTO, itensPedido, formasPagamentoNoPedido);
        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        itensPedido.forEach(itemPedido -> itemPedido.setPedido(pedidoSalvo));

        itemRepository.saveAll(itensPedido);

        BeanUtils.copyProperties(novoPedido, pedidoDTO);

        return enviarPedidoCriadoAoBroker(pedidoDTO);
    }

    private PedidoDTO enviarPedidoCriadoAoBroker(PedidoDTO pedidoDTO) {
        try {
            kafkaPedidoTemplate.send("pedido-criado", pedidoDTO);
            return pedidoDTO;
        } catch (Exception e) {
            throw new RuntimeException("Pedido não pôde ser enviado");
        }

    }

    public PedidoDTO buscar(Long id) {
        try {
            Pedido pedidoLocalizado = buscaPedidoService.buscarPedidoPeloId(id);

            PedidoDTO pedidoResponse = new PedidoDTO();

            BeanUtils.copyProperties(pedidoLocalizado, pedidoResponse);

            return pedidoResponse;
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public PedidoDTO alterar(@Valid PedidoPutDTO pedidoDTO) {
        try {
            Pedido pedidoLocalizado = buscaPedidoService.buscarPedidoPeloId(pedidoDTO);

            BeanUtils.copyProperties(pedidoDTO, pedidoLocalizado);

            pedidoRepository.save(pedidoLocalizado);

            PedidoDTO pedidoResponseDTO = new PedidoDTO();
            BeanUtils.copyProperties(pedidoDTO,pedidoResponseDTO);

            return pedidoResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void desativar(Long id) {
        try {
            Pedido pedido = buscaPedidoService.pesquisarVendaPeloIDOuLancarExcecaoNaoEncontrada(id);
            boolean statusInativo = false;
            pedido.setStatus(statusInativo);
            pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Pedido criarPedido(PedidoDTO pedidoDTO, List<ItemPedido> itensPedido, List<FormaPagamento> formasPagamentoNoPedido) {
        Pedido novoPedido = new Pedido();

        BeanUtils.copyProperties(pedidoDTO, novoPedido);

        novoPedido.setItensPedido(itensPedido);
        novoPedido.setFormasPagamento(formasPagamentoNoPedido);
        return novoPedido;
    }

    private Pedido buscarPedidoPelaData(@Valid PedidoDTO pedidoDTO) {
        return pedidoRepository.findBydataHoraAndNomeUsuario(pedidoDTO.getDataHora(), pedidoDTO.getNomeUsuario()).get();
    }

    private List<Pedido> buscarPedidoPeloUsuario(@Valid PedidoDTO pedidoDTO)  {
        return pedidoRepository.findByNomeUsuario(pedidoDTO.getNomeUsuario());
    }

}
