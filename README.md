# MovieFlix

Uma API REST simples para gerenciar categorias de filmes e plataformas de streaming.

## Descrição

MovieFlix é uma aplicação Spring Boot que fornece endpoints para criar, ler e excluir categorias de filmes, plataformas de streaming e filmes. Foi projetada como um serviço de backend que pode ser utilizado por um catálogo de filmes ou sistema de recomendação.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.3
- Spring Data JPA
- PostgreSQL
- Flyway para migrações de banco de dados
- Docker & Docker Compose
- Lombok
- MapStruct
- SpringDoc OpenAPI
- JWT

## Instruções de Configuração

### Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven

### Executando a Aplicação

1. Clone o repositório
2. Inicie o banco de dados PostgreSQL usando Docker Compose:
   ```
   docker-compose up -d
   ```
3. Compile e execute a aplicação:
   ```
   ./mvnw spring-boot:run
   ```
4. A aplicação estará disponível em http://localhost:8080

## Endpoints da API

### Categorias

- `GET /categories` - Listar todas as categorias
- `GET /categories/{id}` - Obter uma categoria por ID
- `POST /categories` - Criar uma nova categoria
- `DELETE /categories/{id}` - Excluir uma categoria

### Plataformas de Streaming

- `GET /streaming` - Listar todas as plataformas de streaming
- `GET /streaming/{id}` - Obter uma plataforma de streaming por ID
- `POST /streaming` - Criar uma nova plataforma de streaming
- `DELETE /streaming/{id}` - Excluir uma plataforma de streaming

### Filmes

- `GET /movies` - Listar todos os filmes
- `GET /movies/{id}` - Obter um filme por ID
- `POST /movies` - Criar um novo filme
- `DELETE /movies/{id}` - Excluir um filme


## Esquema do Banco de Dados

A aplicação utiliza 3 tabelas, sendo a principal a de `movie`(filmes):

- `category` - Armazena categorias de filmes com nomes únicos
- `streaming` - Armazena plataformas de streaming com nomes únicos
- `movie` - Armazena os filmes

## Documentação da API

A documentação da API está disponível via SpringDoc OpenAPI em http://localhost:8080/swagger-ui.html quando a aplicação estiver em execução.
