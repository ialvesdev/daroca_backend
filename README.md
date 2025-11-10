# 🚀 API de Vendas - Projeto Daroca (Backend)

Esta é uma API REST completa para um sistema de e-commerce, desenvolvida em Java com Spring Boot. O projeto gerencia clientes, produtos, categorias e um sistema transacional de pedidos.

Este projeto foi originalmente um trabalho acadêmico (do Cotuca) e foi totalmente refatorado em 2025 para modernizar a arquitetura, migrar o banco de dados de SQL Server para **PostgreSQL** e implementar as melhores práticas de desenvolvimento de API.

## ✨ Principais Funcionalidades

* **Gerenciamento de Clientes:** CRUD completo para usuários.
* **Gerenciamento de Catálogo:** CRUD para Produtos e Categorias de Produtos.
* **Sistema de Pedidos:** Endpoint transacional (`@Transactional`) para a criação de novos pedidos (`SalesOrder`), garantindo a integridade dos dados ao salvar múltiplos `SalesOrderItems`.
* **Código Limpo:** Uso extensivo de **Lombok** para eliminar *boilerplate code* (getters, setters, construtores) nos *Models*.
* **API Profissional:** Respostas de API padronizadas usando `ResponseEntity` para retornar os códigos de status HTTP corretos (como `200 OK`, `201 CREATED`, `404 NOT FOUND`).
* **DTO (Data Transfer Object):** Uso de DTOs (`SalesOrderRequestDTO`) para receber dados complexos na criação de pedidos, desacoplando a API do modelo de banco de dados.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.x**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL** (Banco de Dados Relacional)
* **Maven** (Gerenciador de dependências)
* **Lombok** (Redução de boilerplate)

---

## ⚙️ Como Executar Localmente

Este projeto **não está em deploy** e deve ser executado localmente.

### Pré-requisitos

* **Java 17 (JDK)**
* **Maven** (Instalado e configurado no `PATH` do sistema)
* **PostgreSQL** (Servidor local rodando, por exemplo, na porta `5432`)
* **pgAdmin** (ou outra ferramenta de gerenciamento de banco)

### Passos para Instalação

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/SEU_USUARIO/daroca_backend.git](https://github.com/SEU_USUARIO/daroca_backend.git)
    cd daroca_backend
    ```

2.  **Crie o Banco de Dados:**
    * Abra o **pgAdmin** e conecte-se ao seu servidor PostgreSQL local.
    * Crie um novo banco de dados (database) chamado exatamente: `darocabd`.

3.  **Configure o `application.properties`:**
    * Na pasta `src/main/resources/`, renomeie o arquivo `application.properties.example` para `application.properties`.
    * Abra o `application.properties` e preencha a senha do seu banco de dados local:
        ```properties
        # Preencha com sua senha do PostgreSQL
        spring.datasource.password=[SUA_SENHA_AQUI]
        ```

4.  **Compile o Projeto (Maven):**
    * Abra um terminal na raiz do projeto e execute:
    ```bash
    mvn clean install
    ```
    *(Isso baixará todas as dependências e compilará o projeto)*

5.  **Execute a Aplicação:**
    * Você pode rodar diretamente pela sua IDE (ex: VS Code, IntelliJ) executando a classe `DarocaApplication.java`.
    * ...ou pelo terminal:
    ```bash
    java -jar target/daroca-0.0.1-SNAPSHOT.jar
    ```

A API estará rodando em `http://localhost:8080`.

---

## 🧪 Guia Rápido de Testes (Fluxo "Happy Path")

Para ver a funcionalidade principal (criação de pedidos) funcionando, o banco de dados precisa ter os dados pré-cadastrados. Siga esta ordem no Insomnia/Postman:

### 1. Crie uma Categoria de Produto

* **Método:** `POST`
* **URL:** `http://localhost:8080/product-categories`
* **Body (JSON):**
    ```json
    {
      "name": "Eletrônicos"
    }
    ```
* *(Guarde o `productCategoryId` da resposta, ex: `1`)*

### 2. Crie um Produto

* **Método:** `POST`
* **URL:** `http://localhost:8080/products`
* **Body (JSON):** (Use o ID da categoria criada acima)
    ```json
    {
      "name": "Mouse Gamer",
      "unitPrice": 150.00,
      "productCategory": {
        "productCategoryId": 1 
      }
    }
    ```
* *(Guarde o `productId` da resposta, ex: `1`)*

### 3. Crie um Cliente

* **Método:** `POST`
* **URL:** `http://localhost:8080/customers`
* **Body (JSON):**
    ```json
    {
      "name": "Cliente de Teste",
      "email": "recrutador@empresa.com"
    }
    ```
* *(Guarde o `id` (customer ID) da resposta, ex: `1`)*

### 4. (O Grande Final) Crie um Pedido

* **Método:** `POST`
* **URL:** `http://localhost:8080/sales-orders`
* **Body (JSON):** (Use os IDs do cliente e do produto criados acima)
    ```json
    {
      "customerId": 1,
      "items": [
        { "productId": 1, "quantity": 2 }
      ]
    }
    ```
* **Resultado:** Você receberá um `201 CREATED` com o pedido completo, itens e total calculados!

---

## 📌 Documentação da API (Endpoints)

### Clientes (`/customers`)

| Método | URL | Descrição | Status (Sucesso) |
| :--- | :--- | :--- | :--- |
| `POST` | `/customers` | Cria um novo cliente. | `201 CREATED` |
| `GET` | `/customers` | Lista todos os clientes. | `200 OK` |
| `GET` | `/customers/{id}` | Busca um cliente por ID. | `200 OK` |
| `PUT` | `/customers/{id}` | Atualiza um cliente. | `200 OK` |
| `DELETE` | `/customers/{id}` | Deleta um cliente. | `204 NO CONTENT` |

### Produtos (`/products`)

| Método | URL | Descrição | Status (Sucesso) |
| :--- | :--- | :--- | :--- |
| `POST` | `/products` | Cria um novo produto. | `201 CREATED` |
| `GET` | `/products` | Lista todos os produtos. | `200 OK` |
| `GET` | `/products/{id}` | Busca um produto por ID. | `200 OK` |
| `PUT` | `/products/{id}` | Atualiza um produto. | `200 OK` |
| `DELETE` | `/products/{id}` | Deleta um produto. | `204 NO CONTENT` |

### Categorias de Produto (`/product-categories`)

| Método | URL | Descrição | Status (Sucesso) |
| :--- | :--- | :--- | :--- |
| `POST` | `/product-categories` | Cria uma nova categoria. | `201 CREATED` |
| `GET` | `/product-categories` | Lista todas as categorias. | `200 OK` |
| `GET` | `/product-categories/{id}` | Busca uma categoria por ID. | `200 OK` |
| `PUT` | `/product-categories/{id}` | Atualiza uma categoria. | `200 OK` |
| `DELETE` | `/product-categories/{id}` | Deleta uma categoria. | `204 NO CONTENT` |

### Pedidos (`/sales-orders`)

| Método | URL | Descrição | Status (Sucesso) |
| :--- | :--- | :--- | :--- |
| `POST` | `/sales-orders` | Cria um novo pedido (Transacional). | `201 CREATED` |
| `GET` | `/sales-orders` | Lista todos os pedidos. | `200 OK` |
| `GET` | `/sales-orders/{id}` | Busca um pedido por ID. | `200 OK` |
| `DELETE` | `/sales-orders/{id}` | Deleta um pedido. | `204 NO CONTENT` |