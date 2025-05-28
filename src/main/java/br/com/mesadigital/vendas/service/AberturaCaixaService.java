package br.com.mesadigital.vendas.service;

import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import br.com.mesadigital.vendas.service.dto.Caixa;
import br.com.mesadigital.vendas.utils.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;

@Service
public class AberturaCaixaService {

    @Autowired
    CaixaService caixaService;

    /**
     * Se para usuário x tem algum registro de abertura de caixa antes do pedidoDTO.dataHora
     * E não tem nenhum fechamento para esse registro de abertura
     * @param pedidoDTO dados do pedido
     * @return Dados do caixa
     */
    @Cacheable(value = "caixas", key = "#pedidoDTO.nomeUsuario")
    public Caixa verificarSeOCaixaEstaAberto(PedidoDTO pedidoDTO) throws BadRequestException {
        String dataHora = DataUtils.toEN_US(pedidoDTO.getDataHora());
        Long idUsuario = 1L; // ir ao serviço de usuário/autenticação buscando pelo nome dele

        Mono<String> retornoConsultaCaixa = caixaService.consultarCaixa(dataHora, idUsuario);
        String resposta = retornoConsultaCaixa.block();
        if(Objects.equals(resposta, "-1")) {
            throw new BadRequestException("Caixa não foi aberto");
        }

        try {
            String caixaId = new ObjectMapper().readTree(resposta).path("id").asText();
            return new Caixa(
                    Long.parseLong(caixaId),
                    pedidoDTO.getNomeUsuario()
            );

        } catch (IOException e) {
            throw new RuntimeException("Não foi possível identificar os dados do caixa", e);
        }

    }
}
