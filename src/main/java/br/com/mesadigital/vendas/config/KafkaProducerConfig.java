package br.com.mesadigital.vendas.config;

import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;
import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, PedidoDTO> pedidoProdutorFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "15000");
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "pedido-produtor");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "br.com.mesadigital.vendas.controller.dto.kafka");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "br.com.mesadigital.vendas.controller.dto.kafka.PedidoOperacoesDTO");


        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, PedidoDTO> orderKafkaTemplate() {
        return new KafkaTemplate<>(pedidoProdutorFactory());
    }
}
