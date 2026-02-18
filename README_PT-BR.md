# API de Validação de Senha

API REST segura para validação de senhas construída com Spring Boot, seguindo arquitetura em camadas e princípios SOLID.

---

## 1. Visão Geral

Este projeto expõe uma API REST responsável por validar senhas de acordo com um conjunto rigoroso e determinístico de regras de segurança.

A implementação prioriza comportamento determinístico e modelagem explícita de regras, evitando validações implícitas ou lógica oculta.  
Todas as regras são modeladas como componentes de domínio de primeira classe, garantindo clareza, previsibilidade e extensibilidade.

A solução foi projetada com ênfase em:

- Separação de responsabilidades
- Baixo acoplamento e alta coesão
- Extensibilidade (Princípio Open/Closed)
- Testabilidade
- Modelagem de domínio clara e explícita

---

## 2. Requisitos Funcionais

Uma senha é considerada válida se:

- Possuir pelo menos 9 caracteres
- Conter pelo menos:
  - 1 letra maiúscula
  - 1 letra minúscula
  - 1 dígito
  - 1 caractere especial do conjunto: `!@#$%^&*()-+`
- Não possuir caracteres repetidos
- Não conter espaços em branco
- Conter apenas caracteres alfanuméricos e o conjunto explícito de caracteres especiais definidos

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

A camada de domínio é agnóstica a frameworks e contém exclusivamente lógica de negócio pura.  
Ela é intencionalmente isolada do Spring e de quaisquer anotações específicas de framework, preservando portabilidade e separação estrita de responsabilidades.

---

## 4. Padrões Arquiteturais

### Arquitetura em Camadas

O projeto separa responsabilidades em camadas distintas:

- **web** → responsabilidades HTTP
- **service** → orquestração da aplicação
- **domain** → regras de negócio
- **config** → composição de dependências

Essa estrutura protege o domínio de dependências de framework e mantém o acoplamento reduzido.

---

### Padrão Composite para Composição de Regras

A validação da senha é implementada utilizando o padrão Composite.

Cada regra implementa:

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

- A validação é delegada à CompositePasswordPolicy, que executa todas as regras configuradas e interrompe na primeira falha (abordagem fail-fast).
- A camada de domínio assume entrada válida (não nula) como pré-condição.
- A validação de transporte (JSON malformado, corpo nulo, campos nulos) é tratada na camada web

---

## 6. Tratamento de Erros e Matriz de Responsabilidades

O sistema separa explicitamente erros de transporte de violações de regras de negócio.

### Erros de Transporte (Camada Web)

Tratados automaticamente pelo Spring MVC e Jackson:

- JSON malformado → 400 Bad Request
- Corpo da requisição ausente → 400 Bad Request
- Campo password nulo → 400 Bad Request
- Content-Type incorreto → 400 Bad Request

Esses erros são considerados violações de contrato HTTP, não falhas de negócio.\
O controller garante que requisições inválidas não alcancem a camada de domínio.\

---

### Camada de Aplicação (Service)

A camada de serviço:
- Orquestra a validação
- Registra o nome da regra que falhou
- Não realiza validação de transporte
- Não contém regras de negócio

---

### Camada de Domínio (Policy & Rules)

A camada de domínio:

- Avalia as regras da senha
- Retorna o resultado da validação
- Não realiza parsing de JSON
- Não trata preocupações HTTP
- Assume que as pré-condições da aplicação foram respeitadas
Essa separação garante que o domínio permaneça puro e independente de frameworks.

## 7. Uso da API

### Endpoint

POST `/api-password/validate`

URL base:

```
http://localhost:8080
```

### Senha Válida (curl)

```
curl -X POST http://localhost:8080/api-password/validate \
  -H "Content-Type: application/json" \
  -d '{"password":"AbTp9!fok"}'
```

### Resposta esperada:

```
{
  "isValid": true
}
```

### Senha Inválida (curl)

```
curl -X POST http://localhost:8080/api-password/validate \
  -H "Content-Type: application/json" \
  -d '{"password":"AbTp9!foo"}'
```

### Resposta esperada:

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

## 8. Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 OK | Validação executada com sucesso |
| 400 Bad Request | Violação do contrato HTTP (corpo nulo, JSON malformado, content type inválido) |
| 500 Internal Server Error | Erro inesperado no servidor |

---

## 9. Observabilidade

O logging é implementado na camada de serviço:

- O valor da senha nunca é registrado
- Apenas o nome da regra que falhou é logado
- Falhas de validação são registradas no nível warn
- O domínio permanece desacoplado do framework

---

## 10. Considerações de Segurança

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

## 11. Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot
- Maven
- JUnit 5
- Lombok

---

## 12. Justificativa da Versão do Java

O projeto utiliza Java 21 (LTS).\

Embora Java 17 fosse suficiente para este caso de uso, o Java 21 foi escolhido estrategicamente devido a:

- Suporte de longo prazo (LTS)
- Alinhamento com o ecossistema atual do Spring Boot
- Melhorias na JVM
- Disponibilidade de Virtual Threads (Project Loom) para futuras evoluções de escalabilidade

---

## 13. Estratégia de Testes

A suíte de testes foi estruturada seguindo a divisão arquitetural da aplicação:

- Testes unitários para cada regra individual (lógica de domínio).
- Testes da política composta para validar o comportamento de agregação das regras.
- Testes do caso de uso para validar a orquestração da aplicação.
- Testes do controller para verificar o contrato HTTP e a estrutura da resposta.
- Testes de integração cobrindo o fluxo completo da requisição.

O Mockito foi aplicado de forma seletiva nos testes da camada de aplicação e configuração, permitindo controlar cenários específicos (como os diferentes retornos de Optional) e validar comportamentos de forma isolada.

O objetivo é garantir correção comportamental, respeito à separação de camadas e prevenir regressões ao adicionar novas regras de validação.

---

## 14. Como Executar

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