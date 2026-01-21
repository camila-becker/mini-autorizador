# Mini Autorizador

## 📌 Descrição
Este projeto foi desenvolvido como solução para o [desafio técnico](./DESAFIO.md) de autorização de transações.  
Ele simula a criação de cartões, consulta de saldo e autorização de transações financeiras, garantindo consistência e concorrência.

---

## 🚀 Tecnologias utilizadas
- **Java 21** → versão mais recente que trabalhei, aproveitando recursos modernos da linguagem.
- **Spring Boot** → para criação da API REST.
- **Maven** → gerenciamento de dependências e build.
- **MySQL** → banco de dados relacional utilizado como persistência principal.
- **Arquitetura Hexagonal** → separação clara entre domínio, casos de uso e adapters, facilitando troca de tecnologias (ex.: migrar para NoSQL como MongoDB).
- **OpenAPI (Swagger)** → documentação dos endpoints.
- **JUnit 5** → testes automatizados.
- **GitHub Actions** → CI/CD para build e execução dos testes.

---

## 🏗️ Decisões de Design

### Java 21 e Sealed Interfaces
- Utilizei **Sealed Interfaces** (introduzidas no Java 17) para controlar os resultados de validações de criação de cartão e autorização de transações.
- Isso evita múltiplos `if`s e permite usar **pattern matching com `switch`**, deixando o código mais expressivo e seguro.

### Persistência
- Banco principal: **MySQL**.
- Arquitetura hexagonal permite criar facilmente novos adapters, como um repositório para **MongoDB**, usando `@Profile` para alternar entre implementações.

### Concorrência
- Utilizei `@Version` na entidade JPA para **lock otimista**, garantindo que duas transações concorrentes não causem inconsistência no saldo.
- Combinei com `@Transactional` nos serviços para manter atomicidade e rollback em caso de falha.

### Senhas
- No desafio, a senha precisava ser retornada na resposta.
- Por isso, tratei a senha como **string sem criptografia**.
- **Decisão consciente:** em um sistema real, a senha deveria ser **criptografada** (ex.: BCrypt) e **nunca retornada** em responses.

---

## 🔐 Autenticação

A API utiliza **Basic Auth** para autenticação.

### Credenciais padrão
- Usuário: `username`
- Senha: `password`

### Como enviar
Inclua o header `Authorization` em cada requisição:

### Exemplo com cURL
Authorization: Basic base64(username:password)
```bash
curl -X POST http://localhost:8080/transacoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=" \
  -d '{
        "numeroCartao": "1111111111111111",
        "senha": "1234",
        "valor": 100.00
      }'
```

---

## 📖 Documentação da API
A documentação dos endpoints está disponível via **OpenAPI/Swagger**.  
Principais endpoints:
- `POST /cartoes` → cria um novo cartão.
- `GET /cartoes/{numeroCartao}` → consulta saldo.
- `POST /transacoes` → autoriza uma transação.

---

## 📖 Exemplos de uso da API

### 1. Criar cartão
**Request**
```http
POST /cartoes
Content-Type: application/json
{
  "numeroCartao": "1234567890123456",
  "senha": "1234"
}
```

**Response**
```http
201 Created
{
  "numeroCartao": "1234567890123456",
  "senha": "1234"
}
```

### 2. Consultar saldo
**Request**
```http
GET /cartoes/1234567890123456
```

**Response**
```http
500.00
```

### 3. Autorizar transação
**Request**
```http
POST /transacoes
Content-Type: application/json

{
  "numeroCartao": "1234567890123456",
  "senhaCartao": "1234",
  "valor": 100.00
}
```

**Response**
```http
200
OK
```

## ⚙️ Como rodar o projeto

### Localmente

```
mvn clean install
mvn spring-boot:run
```

### Via Docker Compose

```
docker-compose up
```

## ✅ Testes

- Testes unitários com JUnit 5.
- Testes de concorrência simulando múltiplas transações simultâneas para validar o uso de `@Version`.
- Testes de integração com banco MySQL.

## 🔄 CI/CD

- Configurado GitHub Actions em .github/workflows/ci.yml.
- Workflow executa:
  - Checkout do código.
  - Setup do JDK 21.
  - Build e testes com Maven.
  - Subida de container MySQL para testes de integração.