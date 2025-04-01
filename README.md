# Seletivo SEPLAG MT - Sistema de Gerenciamento de Servidores

Repositório criado para realização do projeto requisitado pelo seletivo do SEPLAG MT. O projeto implementa uma API REST para gerenciamento de servidores públicos, unidades e lotações.

## Tecnologias Utilizadas

- Java 21
    - Spring Boot 3.4.4
    - JWT para autenticação
- PostgreSQL
- MinIO (para armazenamento de imagens)
- Docker e Docker Compose

## Funcionalidades

- CRUD completo para:
  - Servidores Efetivos
  - Servidores Temporários
  - Unidades
  - Lotações
- Autenticação JWT com expiração em 5 minutos e refresh token
- Upload e gerenciamento de fotos de servidores usando MinIO
- Consulta de servidores por unidade
- Consulta de endereço funcional por nome do servidor
- Links temporários para acesso às fotos (expiração em 5 minutos)
- Paginação em todas as consultas

## Pré-requisitos

- Docker e Docker Compose instalados
- Java 21 instalado (para desenvolvimento)
- Maven instalado (para desenvolvimento)

## Executando a Aplicação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd seletivo-seplag
```

2. Execute os containers usando Docker Compose:
```bash
docker-compose up -d
```

Isso irá iniciar:
- PostgreSQL na porta 5432
- MinIO na porta 9000 (API) e 9001 (Console)
- A aplicação Spring Boot na porta 8080

## Testando a API

1. Autenticação:

Primeiro, crie um usuário:

2. Exemplos de requisições:

Use o token obtido no header Authorization de todas as requisições:


## Acessando o MinIO

O console do MinIO está disponível em:
- URL: http://localhost:9001
- Usuário: minioadmin
- Senha: minioadmin

## Documentação da API

A documentação Swagger está disponível em:
http://localhost:8080/swagger-ui.html

## Banco de Dados

Para acessar o PostgreSQL, caso necessário:
- Host: localhost
- Porta: 5432
- Banco: seletivo_seplag
- Usuário: postgres
- Senha: postgres

## Segurança

- Tokens JWT expiram em 5 minutos
- Refresh tokens disponíveis para renovação de sessão
- CORS configurado para controle de acesso por domínio
- Todas as rotas, exceto autenticação, requerem token válido

## Estrutura do Projeto

- `/src/main/java/com/example/demo/` - Código fonte principal
  - `/config` - Configurações do Spring Boot
  - `/controller` - Controladores REST
  - `/model` - Entidades e DTOs
  - `/repository` - Repositórios JPA
  - `/security` - Configurações de segurança e JWT
  - `/service` - Camada de serviços
