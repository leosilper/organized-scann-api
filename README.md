# 🏍️ Organized Scann API

## 📋 Sobre o Projeto

A **Organized Scann API** é uma aplicação backend desenvolvida com **Spring Boot 3**, voltada para a gestão inteligente de motocicletas em pátios de manutenção e recuperação, utilizando identificação via **RFID**.

As motocicletas são organizadas em portais de classificação:

| ID | Portal                         | Descrição                                |
|----|---------------------------------|------------------------------------------|
| 1  | ⚙️ Manutenção Rápida            | Reparos rápidos                         |
| 2  | 🔧 Manutenção Demorada          | Reparos mais longos                     |
| 3  | 🚓 Boletins de Ocorrência        | Casos de polícia                        |
| 4  | 🧹 Motos Recuperadas / Carcaças  | Motos recuperadas ou sucata              |

> ❗ **Observação**: Os IDs dos portais seguem esta ordem exata.

---

## 📺 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Como Rodar o Projeto](#-como-rodar-o-projeto)
- [Principais Endpoints](#-principais-endpoints)
- [Exemplos de Uso no Postman](#-exemplos-de-uso-no-postman)
- [Documentação Swagger](#-documentação-swagger)
- [Banco de Dados](#-banco-de-dados)
- [Sobre o Grupo](#-sobre-o-grupo)

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security (básico)
- Lombok
- Spring Cache
- H2 Database (Memória)
- Specification API (Filtros dinâmicos)
- Swagger / OpenAPI 3
- Spring DevTools (Hot Reload)

---

## ⚙️ Como Rodar o Projeto

✅ Pré-requisitos:

- Java 17+
- Maven 3.8+

🚀 Executar localmente:

```bash
# Clone o repositório
git clone https://github.com/leosilper/organized-scann-api.git

# Acesse o diretório
cd organized-scann-api

# Rode o projeto
./mvnw spring-boot:run
```

---

## 🔗 Principais Endpoints

| Método  | Endpoint                         | Descrição                                 |
|---------|----------------------------------|-------------------------------------------|
| GET     | `/api/motorcycles`               | Listar motocicletas (paginado, filtrado)   |
| POST    | `/api/motorcycles`               | Cadastrar nova motocicleta                |
| GET     | `/api/motorcycles/{id}`          | Buscar motocicleta por ID                 |
| PUT     | `/api/motorcycles/{id}`          | Atualizar motocicleta                     |
| DELETE  | `/api/motorcycles/{id}`          | Deletar motocicleta                       |
| GET     | `/api/portals/summary`           | Listar resumo dos portais                 |

---

## 🔍 Exemplos de Uso no Postman

### 🔐 1. Autenticar e obter Token JWT

```http
POST http://localhost:8080/auth/login
```

**Body (JSON):**
```json
{
  "email": "admin@example.com",
  "password": "admin123"
}
```

✅ Retorno esperado:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Copie o token retornado e use nos próximos endpoints como **Authorization Header**:

**Exemplo de uso no Postman**:
1. Vá até a aba **Headers**.
2. Adicione:
   ```
   Key: Authorization
   Value: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```
   ⚠️ Atenção: deve começar com `"Bearer "` (com espaço).

   ✅ Agora você estará autenticado para acessar os endpoints protegidos, que estão descrito nos exemplos abaixo!

---

### 🚀 2. Listar todas as motocicletas

```http
GET http://localhost:8080/api/motorcycles
```

✅ Retorna todas as motocicletas cadastradas.

---

### 🚀 3. Buscar motocicletas por filtros

Buscar por placa:

```http
GET http://localhost:8080/api/motorcycles?licensePlate=ABC
```

Buscar por RFID:

```http
GET http://localhost:8080/api/motorcycles?rfid=RFID123
```

Buscar por portal:

```http
GET http://localhost:8080/api/motorcycles?portalId=1
```

Buscar combinando filtros:

```http
GET http://localhost:8080/api/motorcycles?licensePlate=ABC&rfid=RFID123&portalId=1
```

---

### 🚀 4. Cadastrar uma nova motocicleta

```http
POST http://localhost:8080/api/motorcycles
```

**Body (JSON):**
```json
{
  "licensePlate": "XYZ9K88",
  "rfid": "RFID999",
  "problemDescription": "Routine maintenance required",
  "entryDate": "2025-05-01",
  "availabilityForecast": "2025-05-10",
  "portal": {
    "id": 2
  }
}
```

✅ A motocicleta será cadastrada no banco!

---

### 🚀 5. Atualizar uma motocicleta

```http
PUT http://localhost:8080/api/motorcycles/{id}
```

**Body (JSON):**
```json
{
  "licensePlate": "NEW1234",
  "rfid": "NEW_RFID",
  "problemDescription": "NEW_DESCRIPTION",
  "entryDate": "2025-05-02",
  "availabilityForecast": "2025-06-01",
  "portal": {
    "id": 3
  }
}
```

---

### 🚀 6. Deletar uma motocicleta

```http
DELETE http://localhost:8080/api/motorcycles/1
```

✅ Resposta `204 No Content` indica que foi apagado com sucesso.

---

### 🚀 7. Listar resumo dos portais

```http
GET http://localhost:8080/api/portals/summary
```

✅ Exibe todos os portais e quantidade de motos associadas.

---

## 📚 Documentação Swagger

Após rodar o projeto, acesse:

```http
http://localhost:8080/swagger-ui.html
```

✅ Interface gráfica para testar todos os endpoints diretamente!

---

## 🛃️ Banco de Dados

- Banco atual: **H2 Database** (em memória)
- JDBC URL: `jdbc:h2:mem:organized_scann`

> ⚡ Pode ser facilmente adaptado para Oracle, PostgreSQL ou MySQL no `application.properties`.

---

## 👥 Sobre o Grupo

| Nome                        | RM        |
|-----------------------------|-----------|
| Leonardo da Silva Pereira   | 557598    |
| Bruno da Silva Souza        | 94346     |
| Julio Samuel de Oliveira    | 557453    |
