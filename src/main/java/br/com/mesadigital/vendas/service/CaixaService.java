package br.com.mesadigital.vendas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class CaixaService {

    @Value(value = "${mesadigital.urlcaixa}")
    String urlCaixa;

    private final WebClient webClient;

    public CaixaService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Realiza a busca de caixa aberto para o usuário informado com a data e hora de abertura
     * @param dataHoraAbertura data e hora da abertura do caixa
     * @param idUsuario id do usuário
     * @return Mono<String> dados do caixa localizado
     */
    public Mono<String> consultarCaixa(String dataHoraAbertura, Long idUsuario) {

        String urlConsultaCaixa = urlCaixa + "/find";

       return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlConsultaCaixa)
                        .queryParam("dataHoraAbertura",dataHoraAbertura)
                        .queryParam("idUsuario",idUsuario)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5))
               .onErrorResume(er -> Mono.just("-1"));

    }
}
