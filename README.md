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

> ❗ **Observação**: Os IDs dos portais seguem esta ordem de numeração exata.

Além disso, cada motocicleta agora pode ser classificada por tipo:

| Tipo                | Descrição                   |
|---------------------|-----------------------------|
| MOTTU_SPORT_110I    | Honda Sport 110i da Mottu   |
| MOTTU_E             | Mottu Elétrica              |
| MOTTU_POP           | Honda Pop da Mottu          |

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
   ⚠️ Atenção: deve começar com "Bearer " (com espaço).

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

Buscar por tipo de motocicleta:

```http
GET http://localhost:8080/api/motorcycles?type=MOTTU_E
```

Buscar combinando filtros:

```http
GET http://localhost:8080/api/motorcycles?licensePlate=ABC1D23&rfid=RFID123&portalId=1&type=MOTTU_SPORT_110I
```

---

### 🚀 4. Cadastrar uma nova motocicleta

```http
POST http://localhost:8080/api/motorcycles
```

**Body (JSON):**
```json
{
 "branch": "Ipiranga (Zona Sul)",
  "type": "MOTTU_E",
  "licensePlate": "XYZ9K88",
  "chassis": "9C2KC1670FR123456",
  "rfid": "RFID999",
  "portalName": "Manutenção Demorada",
  "problemDescription": "Routine maintenance required",
  "entryDate": "2025-05-01",
  "availabilityForecast": "2025-06-10",
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
  "branch": "",                  // Ex: "Mottu Rio de Janeiro - Centro"
  "type": "",                    // Ex: "MOTTU_E" ou "MOTTU_POP"
  "licensePlate": "",            // Ex: "XYZ9K88"
  "chassis": "",                 // Ex: "9C2KC1670FR123456"
  "rfid": "",                    // Ex: "RFID999"
  "portalName": "",              // Ex: "Manutenção Demorada"
  "problemDescription": "",      // Ex: "Routine maintenance required"
  "entryDate": "",               // Ex: "2025-05-01" (formato: yyyy-MM-dd)
  "availabilityForecast": "",    // Ex: "2025-06-10" (formato: yyyy-MM-dd)
  "portal": {
    "id": 0                      // Ex: 2 (ID do portal existente)
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

## 🏆 Diferenciais Técnicos da API

Além de atender a todos os requisitos obrigatórios do Challenge, esta API foi aprimorada com recursos adicionais que a tornam mais robusta, escalável e pronta para produção.

| 🔧 Recurso                         | 💡 Valor Agregado |
|-----------------------------------|-------------------|
| **Autenticação JWT**              | Implementa segurança real de API com controle de acesso baseado em tokens. |
| **Filtros com Specification JPA** | Permite buscas dinâmicas e flexíveis com múltiplos parâmetros combináveis. |
| **Arquitetura Limpa (MVC)**       | Código organizado em camadas (`controller`, `service`, `repository`, etc.), facilitando manutenção e testes. |
| **Uso de DTOs**                   | Garante segurança e clareza nos dados trafegados entre cliente e servidor. |
| **Handler Global de Exceções**    | Oferece respostas padronizadas e amigáveis para erros, seguindo boas práticas REST. |
| **Seeders de Banco de Dados**     | Popula dados automaticamente para testes e apresentações rápidas do sistema. |
| **Configuração de CORS**          | Permite integração segura com aplicações frontend hospedadas em domínios diferentes. |

> ✅ Todos esses recursos foram adicionados com foco em qualidade de código, experiência do usuário e preparação para ambientes reais de produção.

---

## 👥 Sobre o Grupo

| Nome                        | RM        |
|-----------------------------|-----------|
| Leonardo da Silva Pereira   | 557598    |
| Bruno da Silva Souza        | 94346     |
| Julio Samuel de Oliveira    | 557453    |
