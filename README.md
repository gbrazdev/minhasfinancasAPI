# API de FinançasApp - Organizador de Finanças

Bem-vindo à documentação da API do FinançasApp, a solução de organização financeira baseada em Java e Spring Boot 3. Esta API foi projetada para permitir a integração da funcionalidade de organização financeira em diversos sistemas e plataformas.

## Funcionalidades da API

- Registro de receitas e despesas.
- Categorização automática de transações.
- Consulta de resumo financeiro.
- Geração de relatórios mensais.
- Definição de metas financeiras.

## Tecnologias Utilizadas

- Linguagem: Java
- Framework: Spring Boot 3
- Banco de Dados: Banco de Dados de sua escolha (ex: MySQL, PostgreSQL)
- Autenticação: JWT (JSON Web Tokens)

## Endpoints Principais

- `POST /api/transactions`: Registra uma nova transação (receita ou despesa).
- `GET /api/transactions`: Recupera a lista de transações.
- `GET /api/summary`: Retorna o resumo financeiro.
- `GET /api/reports`: Gera um relatório mensal.
- `POST /api/goals`: Define uma nova meta financeira.

## Autenticação

A API utiliza autenticação JWT (JSON Web Tokens) para proteger os endpoints. Para acessar os endpoints protegidos, você precisa incluir um token JWT válido no cabeçalho da solicitação:

```http
Authorization: Bearer <seu-token-jwt>