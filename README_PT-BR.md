# API de Validação de Senha

API REST segura para validação de senhas construída com Spring Boot, seguindo arquitetura em camadas e princípios SOLID.

---

## 1. Visão Geral

Este projeto expõe uma API REST responsável por validar senhas de acordo com um conjunto rigoroso e determinístico de regras de segurança.

A solução foi desenvolvida com ênfase em:

- Separação de responsabilidades
- Baixo acoplamento e alta coesão
- Extensibilidade (Princípio Open/Closed)
- Testabilidade
- Modelagem de domínio clara e explícita

---

## 2. Requisitos Funcionais

Uma senha é considerada válida quando:

- Possui pelo menos 9 caracteres
- Contém pelo menos:
    - 1 letra maiúscula
    - 1 letra minúscula
    - 1 dígito
    - 1 caractere especial do conjunto: `!@#$%^&*()-+`
- Não possui caracteres repetidos
- Não possui espaços em branco
- Contém apenas caracteres permitidos (alfanuméricos + conjunto especial definido)

A API retorna um booleano indicando se a senha é válida.

---

## 3. Estrutura do Projeto

```
src/main/java/br/com/clrf
│
├── PasswordValidationApplication.java
│
├── config
│   └── PasswordValidationConfig.java
│
├── domain
│   ├── policy
│   │   ├── PasswordPolicy.java
│   │   └── CompositePasswordPolicy.java
│   │
│   └── rules
│       ├── PasswordRule.java
│       ├── HasDigitRule.java
│       ├── HasLowercaseRule.java
│       ├── HasUppercaseRule.java
│       ├── HasSpecialCharRule.java
│       ├── MinLengthRule.java
│       ├── NoRepeatedCharRule.java
│       ├── NoWhiteSpaceRule.java
│       └── OnlyAllowedCharRule.java
│
├── service
│   └── PasswordUseCase.java
│
└── web
    ├── controller
    │   └── PasswordController.java
    └── dto
        ├── PasswordRequest.java
        └── PasswordResponse.java
```

A camada de domínio é independente de framework e contém apenas regras de negócio puras.

---

## 4. Padrões Arquiteturais

### Arquitetura em Camadas

O projeto separa responsabilidades em camadas distintas:

- **web** → responsabilidades HTTP
- **service** → orquestração da aplicação
- **domain** → regras de negócio
- **config** → composição das dependências

Essa abordagem protege o domínio de dependências externas e mantém baixo acoplamento.

---

### Padrão Composite para Composição de Regras

A validação de senha é implementada utilizando uma estrutura do tipo Composite.

Cada regra implementa o contrato comum:

```java
boolean isSatisfiedBy(String password);
```

A classe `CompositePasswordPolicy` agrega múltiplas regras e as avalia sequencialmente.

Benefícios:

- Tratamento uniforme de regras individuais e compostas
- Extensão sem necessidade de modificar a lógica existente
- Testes isolados por regra
- Redução de complexidade condicional

---

## 5. Estratégia de Validação

A validação é delegada ao `CompositePasswordPolicy`, que executa todas as regras configuradas e interrompe na primeira falha.

Isso garante comportamento determinístico e previsível.

---

## 6. Uso da API

### Endpoint

POST `/api-password/validate`

URL base:

```
http://localhost:8080
```

### Senha Válida (curl)

```
curl -X POST http://localhost:8080/api-password/validate   -H "Content-Type: application/json"   -d '{"password":"AbTp9!fok"}'
```

Resposta esperada:

```
{
  "isValid": true
}
```

### Senha Inválida (curl)

```
curl -X POST http://localhost:8080/api-password/validate   -H "Content-Type: application/json"   -d '{"password":"AbTp9!foo"}'
```

Resposta esperada:

```
{
  "isValid": false
}
```

### Testando via Insomnia

1. Criar uma nova requisição POST
2. URL: `http://localhost:8080/api-password/validate`
3. Adicionar o header: `Content-Type: application/json`
4. Body (JSON):

```
{
  "password": "AbTp9!fok"
}
```

5. Clicar em Send

---

## 7. Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 OK | Validação executada com sucesso |
| 400 Bad Request | Corpo da requisição nulo ou inválido |
| 500 Internal Server Error | Erro inesperado no servidor |

---

## 8. Observabilidade

O logging é implementado na camada de serviço:

- O valor da senha nunca é registrado
- Apenas o nome da regra que falhou é logado
- Falhas de validação são registradas no nível warn
- O domínio permanece desacoplado do framework

---

## 9. Considerações de Segurança

- A API não expõe detalhes internos das regras de validação
- Apenas um booleano é retornado ao cliente
- Dados sensíveis (senha) não são registrados em logs
- A validação de entrada é feita de forma defensiva no controller

Possíveis melhorias para ambiente produtivo:

- Rate limiting
- Logging estruturado
- Uso obrigatório de HTTPS
- Limitação de tamanho da requisição

---

## 10. Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot
- Maven
- JUnit 5
- Lombok

---

## 11. Justificativa da Versão do Java

O projeto utiliza Java 21 (LTS).

Embora Java 17 fosse suficiente para este caso de uso, o Java 21 foi escolhido estrategicamente devido a:

- Suporte de longo prazo (LTS)
- Alinhamento com o ecossistema atual do Spring Boot
- Melhorias na JVM
- Disponibilidade de Virtual Threads (Project Loom) para futuras evoluções de escalabilidade

---

## 12. Estratégia de Testes

O projeto inclui:

- Testes unitários para cada regra
- Testes da política composta
- Testes do caso de uso
- Testes do controller
- Testes de integração

O objetivo é garantir isolamento comportamental e prevenir regressões ao adicionar novas regras.

---

## 13. Como Executar

### Requisitos

- Java 21+
- Maven 3.9+

### Build

```
mvn clean install
```

### Executar

```
mvn spring-boot:run
```

A aplicação será iniciada em:

```
http://localhost:8080
```
---

## Licença

MIT License