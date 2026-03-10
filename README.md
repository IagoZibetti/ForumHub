# 🚀 ForumHub Alura

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-red?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Spring%20Security-JWT-green?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Maven-Build-red?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge"/>
</p>

<p align="center">
API para gerenciamento do <strong>fórum de discussões</strong> com autenticação segura utilizando <strong>JWT</strong>.
</p>

<p align="center">
Projeto desenvolvido para o challenge <strong>ForumHub</strong> da formação  
<strong>Oracle Next Education (ONE) Alura</strong>.
</p>

---

# 📑 Índice

- [📌 Sobre o Projeto](#-sobre-o-projeto)
- [⚙️ Tecnologias Utilizadas](#️-tecnologias-utilizadas)
- [✨ Funcionalidades](#-funcionalidades)
- [📊 Regras de Negócio](#-regras-de-negócio)
- [🚀 Como Executar o Projeto](#-como-executar-o-projeto)
- [🔗 Endpoints da API](#-endpoints-da-api)
- [🗄 Estrutura do Banco de Dados](#-estrutura-do-banco-de-dados)
- [🔐 Segurança](#-segurança)
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [🧪 Testando a API](#-testando-a-api)
- [🐞 Solução de Problemas](#-solução-de-problemas)
- [📜 Licença](#-licença)

---

# 📌 Sobre o Projeto

O **ForumHub** é uma aplicação backend responsável pelo gerenciamento de tópicos de um fórum de discussão.

A API permite:

- Cadastro de usuários
- Autenticação utilizando **JWT**
- Criação e gerenciamento de tópicos
- Controle de acesso baseado no **autor do tópico**

A aplicação foi construída utilizando **Spring Boot** seguindo boas práticas de:

- Arquitetura em camadas
- API REST
- Segurança com Spring Security
- Persistência com JPA

---

# ⚙️ Tecnologias Utilizadas

| Tecnologia | Descrição |
|------------|-----------|
| Java 17 | Linguagem principal |
| Spring Boot 3.2.3 | Framework backend |
| Spring Security | Autenticação e autorização |
| JWT | Autenticação baseada em token |
| Spring Data JPA | Persistência de dados |
| PostgreSQL | Banco de dados relacional |
| Lombok | Redução de código boilerplate |
| Maven | Gerenciamento de dependências |

---

# ✨ Funcionalidades

## 👤 Autenticação

- Registro de usuários
- Login com geração de **Token JWT**
- Criptografia de senha com **BCrypt**
- Validação automática de token em rotas protegidas

---

## 💬 Gerenciamento de Tópicos

| Operação | Endpoint | Autenticação |
|----------|----------|--------------|
| Criar tópico | `POST /topicos` | ✅ |
| Listar tópicos | `GET /topicos` | ❌ |
| Buscar tópico | `GET /topicos/{id}` | ❌ |
| Atualizar tópico | `PUT /topicos/{id}` | ✅ |
| Deletar tópico | `DELETE /topicos/{id}` | ✅ |

---

# 📊 Regras de Negócio

✔ Não permite **tópicos duplicados** (mesmo título + mensagem)  
✔ Apenas **usuários autenticados** podem criar tópicos  
✔ Apenas o **autor do tópico** pode editar ou excluir  
✔ **Data de criação automática**

---

# 🚀 Como Executar o Projeto

## 1️⃣ Clonar o repositório

```bash
git clone
```

## 2️⃣ Entrar no diretório

```bash
cd ForumHub
```

## 3️⃣ Criar o banco de dados

```sql
CREATE DATABASE ForumHub;
```

## 4️⃣ Configurar credenciais

Arquivo:

```
src/main/resources/application.properties
```

```properties
spring.datasource.password=${FORUM_HUB_SENHA}
```

## 5️⃣ Executar aplicação

```bash
mvn clean install
mvn spring-boot:run
```

Servidor disponível em:

```
http://localhost:8080
```

---

# 🔗 Endpoints da API

## 🔐 Autenticação

### Registrar Usuário

```
POST /auth/registro
```

Body:

```json
{
  "login": "usuario",
  "senha": "senha",
  "nome": "Nome",
  "email": "email@examplo.com"
}
```

Resposta:

```json
{
  "id": 1,
  "login": "usuario",
  "nome": "Nome",
  "email": "email@examplo.com",
  "message": "Usuário cadastrado com sucesso!"
}
```

---

### Login

```
POST /auth/login
```

Body:

```json
{
  "login": "usuario",
  "senha": "senha"
}
```

Resposta:

```json
{
  "token": "TokenGerado"
}
```

---

# 📚 Tópicos

## Criar Tópico

```
POST /topicos
```

Header:

```
Authorization: Bearer {token}
```

Body:

```json
{
  "titulo": "Dúvida sobre Java",
  "mensagem": "Como instalar?",
  "nomeCurso": "Curso Iniciante"
}
```

Resposta:

```json
{
  "id": 1,
  "titulo": "Dúvida sobre Java",
  "mensagem": "Como instalar?",
  "dataCriacao": "2026-10-03T10:30:00",
  "status": "RESPONDIDO",
  "autor": "Nome",
  "curso": "Curso Iniciante"
}
```

---

## Listar Tópicos

```
GET /topicos
```

---

## Buscar Tópico

```
GET /topicos/{id}
```

---

## Atualizar Tópico

```
PUT /topicos/{id}
```

Body:

```json
{
  "titulo": "Dúvida resolvida",
  "mensagem": "Consegui configurar",
  "status": "SOLUCIONADO"
}
```

---

## Deletar Tópico

```
DELETE /topicos/{id}
```

---

# 📊 Códigos de Status

| Código | Significado |
|------|------|
| 200 | Sucesso |
| 201 | Criado |
| 400 | Dados Inválidos |
| 401 | Token Inválido ou Expirado |
| 403 | Sem Permissão |
| 404 | Recurso não encontrado |
| 500 | Erro Interno da API |

---

# 🗄 Estrutura do Banco de Dados

### Tabela `usuarios`

```sql
id BIGSERIAL PRIMARY KEY
login VARCHAR UNIQUE NOT NULL
senha VARCHAR NOT NULL
nome VARCHAR
email VARCHAR UNIQUE
```

### Tabela `cursos`

```sql
id BIGSERIAL PRIMARY KEY
nome VARCHAR UNIQUE NOT NULL
categoria VARCHAR
```

### Tabela `topicos`

```sql
id BIGSERIAL PRIMARY KEY
titulo VARCHAR NOT NULL
mensagem TEXT NOT NULL
data_criacao TIMESTAMP NOT NULL
status VARCHAR NOT NULL
autor_id BIGINT REFERENCES usuarios(id)
curso_id BIGINT REFERENCES cursos(id)
```

---

# 🔐 Segurança

### Fluxo de autenticação

```
Usuário registra conta
        ↓
Senha criptografada com BCrypt
        ↓
Login gera Token JWT
        ↓
Token enviado no header Authorization
        ↓
Spring Security valida o token
```

Header obrigatório:

```
Authorization: Bearer {token}
```

Configuração JWT:

```properties
api.security.token.secret=${JWT_SECRET}
api.security.token.expiration=3000000
```

⚠ Em produção utilize **variáveis de ambiente**.

---

# 📁 Estrutura do Projeto

```
src/main/java/com/ForumHub

ForumHubApplication.java

controller/
 ├ AuthenticationController.java
 └ TopicoController.java

dto/
 ├ LoginRequest.java
 ├ RegisterRequest.java
 ├ RegisterResponse.java
 ├ TokenResponse.java
 ├ TopicoCreateRequest.java
 ├ TopicoUpdateRequest.java
 └ TopicoResponse.java

exception/
 └ GlobalExceptionHandler.java

model/
 ├ Usuario.java
 ├ Topico.java
 └ Curso.java

repository/
 ├ UsuarioRepository.java
 ├ TopicoRepository.java
 └ CursoRepository.java

security/
 ├ SecurityConfiguration.java
 └ SecurityFilter.java

service/
 ├ AuthenticationService.java
 ├ TokenService.java
 └ TopicoService.java
```

---

# 🧪 Testando a API

### Registrar usuário

```
POST http://localhost:8080/auth/registro
```

### Fazer login

```
POST http://localhost:8080/auth/login
```

### Criar tópico

```
POST http://localhost:8080/topicos
```

---

# 🐞 Solução de Problemas

### Erro de conexão com o banco de dados

- Verifique se PostgreSQL está rodando
- Verifique se o banco `ForumHub` realmente existe
- Verifique as credenciais de acesso

### Erro 401

- Token inválido
- Token expirado

### Erro 403

- Apenas o autor pode modificar o tópico

---

# 📜 Licença

Este projeto está licenciado sob a **MIT License**.

Consulte o arquivo **LICENSE** para mais detalhes.

---

<p align="center">
Desenvolvido para o Challenge ForumHub  
<strong>ONE Alura</strong>
</p>
