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

Após iniciar os dockers da seção anterior, a documentação Swagger estará disponível em:
http://localhost:8080/swagger-ui.html

**1. Autenticação:** 

Primeiro, crie um usuário:

![image](https://github.com/user-attachments/assets/45ab9ad6-d44d-45bd-9b82-88943fb38ed2)

E então, requisite um novo token:

![image](https://github.com/user-attachments/assets/1b5cd40c-466c-49c5-acf0-f8973e7be2ab)

Para as próximas requests, será necessário enviar o token, isso é possível clicando no cadeado do swagger e inserindo o token:

![image](https://github.com/user-attachments/assets/29044a66-02df-4b14-bba0-db0aca4a03fa)



**2. Requisitos da API:**

### Requisitos Gerais:
A. Implementar mecanismo de autorização e autenticação, bem como não permitir acesso ao endpoint a partir de domínios diversos do qual estará hospedado o serviço;
- Demonstrado acima
B. A solução de autenticação deverá expirar a cada 5 minutos e oferecer a possibilidade de renovação do período;
- OK
C. Implementar pelo menos os verbos post, put, get;
- OK
D. Conter recursos de paginação em todas as consultas;
- OK
E. Os dados produzidos deverão ser armazenados no servidor de banco de dados previamente criado em container;
- OK
F. Orquestrar a solução final utilizando Docker Compose de modo que inclua todos os contêineres utilizados.
- OK

### Requisitos Específicos:
Implementar uma API Rest para o diagrama de banco de dados acima tomando por base as seguintes orientações:
- Criar um CRUD para Servidor Efetivo, Servidor Temporário, Unidade e Lotação. Deverá ser contemplado a inclusão e edição dos dados das tabelas relacionadas;
  - OK
- Criar um endpoint que permita consultar os servidores efetivos lotados em determinada unidade parametrizando a consulta pelo atributo unid_id; Retornar os seguintes campos: Nome, idade, unidade de lotação e fotografia;
  - OK, disponível em: **GET /servidor-efetivo/unidade/{unidadeId}** via Swagger
- Criar um endpoint que permita consultar o endereço funcional (da unidade onde o servidor é lotado) a partir de uma parte do nome do servidor efetivo.
  - OK, disponível em: **GET /servidor-efetivo/endereco-unidade** via Swagger 
- Realizar o upload de uma ou mais fotografias enviando-as para o Min.IO; A recuperação das imagens deverá ser através de links temporários gerados pela biblioteca do Min.IO com tempo de expiração de 5 minutos
  - OK, pode ser realizado pelos endpoints **POST /servidor-temporario/{id}/fotos** e **POST /servidor-efetivo/{id}/fotos**

## Acessando o MinIO

O console do MinIO está disponível em:
- URL: http://localhost:9001
- Usuário: minioadmin
- Senha: minioadmin

## Documentação da API


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
