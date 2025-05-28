FROM alpine/java:21.0.4-jre
COPY target/pedidos-0.0.1-SNAPSHOT.jar vendas.jar
ENTRYPOINT ["java", "-jar", "/vendas.jar"]
