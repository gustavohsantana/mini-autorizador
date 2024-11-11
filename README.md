
# Mini Autorizador

Este projeto é uma API que simula a funcionalidade de um sistema de autorização de cartões de crédito/débito. Foi desenvolvido utilizando Java 17, Spring Boot, Spring Security, Hibernate, Spring Data e MySQL. A seguir, apresento os detalhes da aplicação, como funciona, os endpoints disponíveis e exemplos de entradas e saídas.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security** (para validação de segurança)
- **Spring Data JPA** (para integração com banco de dados)
- **Hibernate** (para persistência de dados)
- **MySQL** (para armazenamento de dados)
- **Docker** (para containerização do MySQL)

## Instalação e Configuração

Para configurar e executar o projeto, siga os passos abaixo:

1. **Clone o repositório**:
```bash
git clone https://github.com/gustavohsantana/mini-autorizador.git
cd mini-autorizador
```

2. **Inicie o container do MySQL**:
```bash
docker-compose up -d
```

3. **Execute o projeto**:
```bash
./mvnw spring-boot:run
```

## Endpoints da API

### 1. **Criar Cartão**
- **Método**: POST
- **URL**: `/cartoes/criar`
- **Descrição**: Cria um novo cartão com saldo inicial de R$ 500,00.

#### Entrada:
```json
{
    "numeroCartao": "1234567890123456",
    "senha": "1234"
}
```
#### Saída Esperada:
- **Código de Status**: 201 (Created)
- **Corpo da Resposta**:
```json
{
    "numeroCartao": "1234567890123456",
    "senha": "1234",
    "saldo": 500.00
}
```
#### Print da Resposta:
![img.png](img.png)
---

### 2. **Obter Saldo do Cartão**
- **Método**: GET
- **URL**: `/cartoes/{numeroCartao}/saldo`
- **Descrição**: Retorna o saldo de um cartão específico.

#### Entrada:
- **Número do Cartão**: `1234567890123456`

#### Saída Esperada:
- **Código de Status**: 200 (OK)
- **Corpo da Resposta**:
```json
500.00
```
#### Print da Resposta:
![img_1.png](img_1.png)
---

### 3. **Listar Todos os Cartões**
- **Método**: GET
- **URL**: `/cartoes/listar`
- **Descrição**: Retorna todos os cartões cadastrados no sistema.

#### Entrada: Nenhuma

#### Saída Esperada:
- **Código de Status**: 200 (OK)
- **Corpo da Resposta**:
```json
[
    {
        "numeroCartao": "1234567890123456",
        "senha": "1234",
        "saldo": 500.00
    },
    {
        "numeroCartao": "9876543210987654",
        "senha": "4321",
        "saldo": 1000.00
    }
]
```
#### Print da Resposta:
![img_3.png](img_3.png)
---

### 4. **Realizar Transação**
- **Método**: POST
- **URL**: `/cartoes/transacao`
- **Descrição**: Realiza uma transação de débito no cartão especificado.

#### Entrada:
```json
{
    "numeroCartao": "1234567890123456",
    "senhaCartao": "1234",
    "valor": "100.00"
}
```

#### Saída Esperada:
- **Código de Status**: 200 (OK) - Caso a transação seja bem-sucedida
- **Mensagem**:
```json
"Transação realizada com sucesso!"
```

#### Print da Resposta:
![img_5.png](img_5.png)
---

## Validação de Segurança
A aplicação utiliza Spring Security para proteger os endpoints, garantindo que apenas usuários autenticados possam realizar transações. A autenticação é feita por meio de Basic Auth.

### Proteção dos Endpoints
* Authentication Method: Basic Auth
* Username: `username` 
* Password: `password`
* 
Para validar a segurança, as credenciais de autenticação devem ser enviadas no cabeçalho da requisição conforme mostrado abaixo:

## Request Header:
Authorization: Basic <base64-encoded-credentials>

Response for Access Denied (Invalid or Missing Credentials):
- **Status Code**: 401 (Unauthorized)
- **Message**:
```json
{
  "error": "Unauthorized."
} 
```

#### Print da Validação de Segurança:
![img_4.png](img_4.png)
---

## Mapeamento de Erros

A API possui as seguintes exceções mapeadas:

### 1. **Cartão Não Encontrado**
- **Código de Status**: 404 (Not Found)
- **Mensagem de Erro**:
```json
{
    "error": "Cartão não encontrado"
}
```

### 2. **Senha Inválida**
- **Código de Status**: 400 (Bad Request)
- **Mensagem de Erro**:
```json
{
    "error": "Senha inválida"
}
```

### 3. **Saldo Insuficiente**
- **Código de Status**: 400 (Bad Request)
- **Mensagem de Erro**:
```json
{
    "error": "Saldo insuficiente"
}
```

### 4. **Erro Interno do Servidor**
- **Código de Status**: 500 (Internal Server Error)
- **Mensagem de Erro**:
```json
{
    "error": "Erro interno do servidor"
}
```
