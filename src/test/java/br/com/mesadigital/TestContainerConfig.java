package br.com.mesadigital;

import br.com.mesadigital.vendas.VendasApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(classes = VendasApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TestContainerConfig {

    @Autowired
    protected MockMvc mockMvc;

    protected static WebClient webClient;

    protected static ObjectMapper objectMapper = new ObjectMapper();

    private static final Network NETWORK = Network.newNetwork();

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.4-alpine")
            .withUsername("postgres")
            .withDatabaseName("vendas")
            .withPassword("secret")
            .withNetwork(NETWORK)
            .withNetworkAliases("postgres-db") // nome para outros containers acessarem
            .withCommand("postgres", "-c", "listen_addresses=*")
            .withReuse(true)
            .withExposedPorts(5432);



    @Container
    public static GenericContainer<?> redis = new GenericContainer<>("redis:7.2")
            .withExposedPorts(6379)
            .withReuse(true);


    @Container
    public static GenericContainer<?> financeiroContainer = new GenericContainer<>("hiranneri/financeiro-service:latest")
            .dependsOn(postgreSQLContainer)
            .withNetwork(NETWORK)
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://postgres-db:5432/vendas") // usa o alias
            .withEnv("SPRING_DATASOURCE_USERNAME", "postgres")
            .withEnv("SPRING_DATASOURCE_PASSWORD", "secret")
            .withCommand("sh", "-c", "apk add --no-cache postgresql-client && pg_isready -h postgres-db -p 5432")
            .withExposedPorts(8082)
            .withReuse(true);


    public static String getFinanceiroBaseURL() {
        String hostContainerFinanceiro = financeiroContainer.getHost();
        Integer portContainerFinanceiro = financeiroContainer.getMappedPort(8082);

        return "http://" + hostContainerFinanceiro + ":" + portContainerFinanceiro;
    }

    @Container
    public static GenericContainer<?> zookeeperContainer = new GenericContainer<>("confluentinc/cp-zookeeper:7.3.0")
            .withNetwork(NETWORK)
            .withNetworkAliases("zookeeper")
            .withReuse(true);


    @Container
    public static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.3.0")
                                .asCompatibleSubstituteFor("confluentinc/cp-kafka"));

    private static String portaPostgreSQL(){
        return postgreSQLContainer.getMappedPort(5432).toString();
    }


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.hikari.maxLifetime", () -> "300000");
        registry.add("spring.datasource.hikari.maximumPoolSize", () -> "100");
        registry.add("spring.datasource.testWhileIdle", () -> "true");
        registry.add("spring.datasource.testcontainers.properties-on-borrow", () -> "true");


        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);

        // Producer configs
        registry.add("spring.kafka.producer.key-serializer",
                () -> "org.apache.kafka.common.serialization.StringSerializer");
        registry.add("spring.kafka.producer.value-serializer",
                () -> "org.springframework.kafka.support.serializer.JsonSerializer");
        registry.add("spring.kafka.producer.properties.spring.json.add.type.headers", () -> "true");
        registry.add("spring.kafka.producer.properties.request.timeout.ms", () -> "15000");
        registry.add("spring.kafka.producer.properties.retry.backoff.ms", () -> "1000");
        registry.add("spring.kafka.producer.client-id", () -> "pedido-produtor");

        // Consumer configs (caso tenha consumidor no teste)
        registry.add("spring.kafka.consumer.key-deserializer",
                () -> "org.apache.kafka.common.serialization.StringDeserializer");
        registry.add("spring.kafka.consumer.value-deserializer",
                () -> "org.springframework.kafka.support.serializer.JsonDeserializer");
        registry.add("spring.kafka.consumer.properties.spring.json.trusted.packages",
                () -> "br.com.mesadigital.vendas.controller.dto.kafka");
        registry.add("spring.kafka.consumer.properties.spring.json.value.default.type",
                () -> "br.com.mesadigital.vendas.controller.dto.kafka.PedidoOperacoesDTO");
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");

        registry.add("spring.kafka.consumer.group-id", () -> "testcontainers.properties-group");

        registry.add("spring.data.redis.host", ()-> "localhost");
        registry.add("spring.data.redis.port", ()-> redis.getMappedPort(6379).toString());

        registry.add("mesadigital.urlcaixa", ()-> "/caixas");
        registry.add("mesadigital.urlBase", ()-> "http://localhost:" + financeiroContainer.getMappedPort(8082));


    }

    @BeforeAll
    static void setUp() {
        // Registrando o módulo para Java 8 Time API (LocalDateTime, LocalDate, etc)
        objectMapper.registerModule(new JavaTimeModule());

        // Configurando o formato de datas
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        webClient = WebClient.create(getFinanceiroBaseURL());

    }


}
