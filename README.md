# vendas 
Este projeto é um serviço Restful que faz a gestão de pedidos e produtos. Foi desenvolvido em Java usando o _‘Framework’_ Spring Boot com JPA persistindo os dados no banco PostgreSQL.

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/hiranneri/vendas.git
```

Crie os containers com o docker compose

```bash
  docker compose -f docker-compose-qa.yml up
```

## Requisitos
- Java 11
- Docker e Docker Compose v2
- Maven

## Como testar?
Como executar os testes de integração:

Para os testes de integração, apenas execute o comando abaixo:

```bash
mvn clean verify package -Pintegration-tests -Dspring.profiles.active=qa
```
O comando acima irá "buildar" o projeto e executar os testes configurados para o profile integration-tests

## Decisões Técnicas
A escolha do ‘framework’ para o desenvolvimento deste projeto foi fundamentada em dois principais critérios: primeiro, na base de conhecimento prévia que possuo, facilitando a implementação; segundo, na capacidade do framework de suportar os conceitos de inversão de controle e injeção de dependências. Além disso, a escolha recai sobre que o ‘framework’ é desenvolvido numa linguagem amplamente adotada no mercado atual.

O projeto utiliza o padrão Model-Controller, onde a camada Model abstrai os conceitos de banco de dados, enquanto a camada Controller recebe as requisições e encaminha os dados para tratamento e validação na camada Service. Após a validação, os dados são persistidos ou consultados no banco de dados por meio da camada Repository.

A escolha do Docker foi motivada pela facilidade de "conteinerizar" e executar a aplicação e as suas partes em ambientes isolados.
Além disso, a decisão de utilizar a imagem JRE (Java Runtime Environment) foi baseada em considerações de segurança e desempenho, uma vez que essas imagens são mais leves e não contêm componentes que possam introduzir vulnerabilidades de segurança.

Para os testes de integração que envolvem operações de escrita e leitura no banco de dados, foi escolhida a ferramenta TestContainer, essa escolha se deu devido à sua capacidade de gerir o ciclo de vida do container durante a execução dos testes, garantindo a integridade e confiabilidade dos testes.

# Decisões de arquitetura
Além do banco PostgreSQL para persistência dos dados de produtos e pedidos, foi incluído o Redis como banco de _cache_ distribuído, assim em algumas consultas de caixa e usuário podemos ter mais desempenho no retorno dos dados.

Na inicialização do projeto, o liquibase executa as alterações no banco configuradas nos arquivos .xml da pasta [changelog](src/main/resources/db/changelog)

![Diagrama dos serviços](https://github.com/hiranneri/vendas/blob/main/src/main/resources/images/diagrama.png)