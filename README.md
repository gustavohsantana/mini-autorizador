
# Mini Autorizador de Cartões 💳

Este projeto implementa um sistema simples de **Autorização de Transações de Cartões**, que permite criar cartões de crédito, consultar saldo, realizar transações e verificar se as operações são válidas com base no saldo e na senha do cartão.

## Tecnologias Utilizadas 🚀

- **Java 17**: Utilizado como linguagem principal do projeto, garantindo compatibilidade com as versões mais recentes da plataforma e recursos de desempenho aprimorados.
- **Spring Boot**: Framework utilizado para criar a API RESTful de forma rápida e eficiente.
- **Spring Data JPA**: Usado para persistência de dados, facilitando a comunicação com o banco de dados relacional MySQL através de **Hibernate**.
- **Spring Security**: Implementado para garantir segurança nas operações sensíveis, como autenticação e autorização de transações.
- **MySQL**: Banco de dados relacional utilizado para armazenar os dados dos cartões e transações.
- **Docker**: Utilizado para rodar o banco de dados MySQL em um container, facilitando a configuração e gerenciamento do banco de dados.
- **Lombok**: Biblioteca para reduzir o boilerplate de código, especialmente em relação a getters, setters, e outros métodos repetitivos.
- **Spring Boot Starter Web**: Para a criação de APIs RESTful com facilidade.
- **Spring Boot Starter Validation**: Utilizado para validar as entradas do usuário, garantindo que as transações sejam realizadas de forma correta.

## Funcionalidades 💡

- **Criação de Cartões**: Permite a criação de cartões de crédito com saldo inicial de R$ 500,00.
- **Consulta de Saldo**: Consulta o saldo disponível de um cartão através de uma requisição GET.
- **Listagem de Cartões**: Exibe todos os cartões cadastrados no sistema.
- **Realização de Transações**: Efetua transações de débito em um cartão, validando o saldo e a senha fornecida.

## Como Rodar 🏃‍♂️

### Pré-requisitos 🔧

1. **Java 17**: O projeto foi desenvolvido com a versão 17 do Java, portanto, é necessário ter o JDK 17 instalado.
2. **Docker**: Para rodar o banco de dados MySQL em um container.

### Passo a Passo 📜

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/miniautorizador.git
   cd miniautorizador
   ```

2. **Rodar o Banco de Dados MySQL no Docker**:
   Caso ainda não tenha o Docker instalado, siga as instruções [aqui](https://www.docker.com/get-started).
   Rode o seguinte comando para levantar o MySQL em um container Docker:
   ```bash
   docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cartao_db -p 3306:3306 -d mysql:latest
   ```

3. **Rodar o Aplicativo**:
   Com o banco de dados em funcionamento, execute o projeto Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```
   Você também pode rodar o projeto diretamente da sua IDE (IntelliJ, Eclipse, etc.).

4. **Acessar a API**:
   A API estará disponível em `http://localhost:8080/cartoes`. Você pode utilizar ferramentas como Postman ou cURL para interagir com os endpoints.

## Endpoints 📍

### 1. **POST /cartoes/criar**
Cria um novo cartão com saldo inicial de R$ 500,00.

- **Exemplo de Entrada (Request Body)**:
    ```json
    {
      "numeroCartao": "1234567890123456",
      "senha": "1234"
    }
    ```

- **Exemplo de Saída (Response)**:
    ```json
    {
      "message": "Cartão criado com sucesso",
      "numeroCartao": "1234567890123456",
      "saldo": 500.00
    }
    ```

- **Possíveis Erros**:
    - **400 Bad Request**: Caso o número do cartão seja inválido ou a senha não seja fornecida.
    - **409 Conflict**: Caso o cartão já exista.

---

### 2. **GET /cartoes/{numeroCartao}/saldo**
Consulta o saldo de um cartão especificado pelo número.

- **Exemplo de Entrada (Request)**:
    - URL: `http://localhost:8080/cartoes/1234567890123456/saldo`

- **Exemplo de Saída (Response)**:
    ```json
    {
      "numeroCartao": "1234567890123456",
      "saldo": 500.00
    }
    ```

- **Possíveis Erros**:
    - **404 Not Found**: Caso o cartão não seja encontrado.

---

### 3. **GET /cartoes/listar**
Lista todos os cartões cadastrados.

- **Exemplo de Entrada (Request)**:
    - URL: `http://localhost:8080/cartoes/listar`

- **Exemplo de Saída (Response)**:
    ```json
    [
      {
        "numeroCartao": "1234567890123456",
        "saldo": 500.00
      },
      {
        "numeroCartao": "9876543210123456",
        "saldo": 300.00
      }
    ]
    ```

- **Possíveis Erros**:
    - **500 Internal Server Error**: Caso haja um erro no servidor.

---

### 4. **POST /cartoes/transacao**
Realiza uma transação de débito em um cartão, verificando o saldo e a senha.

- **Exemplo de Entrada (Request Body)**:
    ```json
    {
      "numeroCartao": "1234567890123456",
      "senhaCartao": "1234",
      "valor": 50.00
    }
    ```

- **Exemplo de Saída (Response)**:
    ```json
    {
      "message": "Transação realizada com sucesso",
      "numeroCartao": "1234567890123456",
      "novoSaldo": 450.00
    }
    ```

- **Possíveis Erros**:
    - **400 Bad Request**: Caso o valor da transação seja inválido ou a senha não seja fornecida.
    - **404 Not Found**: Caso o cartão não seja encontrado.
    - **403 Forbidden**: Caso a senha fornecida esteja incorreta.
    - **400 Bad Request**: Caso o valor da transação seja superior ao saldo disponível.

## Estrutura do Projeto 📂

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── vrbeneficios/
│   │           └── miniautorizador/
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── exceptions/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── service/
│   │               └── validator/
│   └── resources/
│       └── application.properties
└── test/
```

## Exceções e Validações 🛠️

- **Cartão Não Encontrado**: Caso o número do cartão não seja encontrado no banco de dados.
- **Senha Inválida**: Caso a senha fornecida para o cartão esteja incorreta.
- **Saldo Insuficiente**: Caso o valor da transação seja maior do que o saldo disponível no cartão.

## Observações ⚠️

- O sistema foi projetado para ser simples e focado na simulação de transações financeiras, sem a persistência do saldo entre reinicializações do sistema (exceto no ambiente real com MySQL).
- A aplicação pode ser escalada com o uso de **Docker** para o banco de dados e outras tecnologias, dependendo da necessidade de produção.
