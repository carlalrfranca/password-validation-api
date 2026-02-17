# Password Validation API

Secure password validation API built with Spring Boot, following layered
architecture and SOLID principles.

------------------------------------------------------------------------

## 1. Overview

This project exposes a REST API responsible for validating passwords
according to a strict and deterministic set of security rules.

The solution was designed with emphasis on:

-   Separation of concerns
-   Low coupling and high cohesion
-   Extensibility (Open/Closed Principle)
-   Testability
-   Clean and explicit domain modeling

------------------------------------------------------------------------

## 2. Functional Requirements

A password is considered valid if it:

-   Contains at least 9 characters
-   Contains at least:
    -   1 uppercase letter
    -   1 lowercase letter
    -   1 digit
    -   1 special character from the set: `!@#$%^&*()-+`
-   Contains no repeated characters
-   Contains no whitespace
-   Contains only allowed characters (alphanumeric + defined special
    set)

The API returns a boolean indicating whether the password is valid.

------------------------------------------------------------------------

## 3. Project Structure

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

The domain layer is framework-agnostic and contains only pure business
logic.

------------------------------------------------------------------------

## 4. Architectural Patterns

### Layered Architecture

The project separates responsibilities into distinct layers:

-   web → HTTP concerns\
-   service → application orchestration\
-   domain → business rules\
-   config → dependency composition

This protects the domain from framework dependencies and keeps coupling
low.

### Composite Pattern for Rule Composition

Password validation is implemented using a Composite structure.

Each rule implements:

``` java
boolean isSatisfiedBy(String password);
```

`CompositePasswordPolicy` aggregates multiple rules and evaluates them
sequentially.

Benefits:

-   Uniform treatment of single and multiple rules
-   Extension without modifying existing validation logic
-   Isolated rule-level testing
-   Reduced conditional complexity

------------------------------------------------------------------------

## 5. Validation Strategy

Validation is delegated to `CompositePasswordPolicy`, which executes all
configured rules and stops at the first failure.

------------------------------------------------------------------------

## 6. API Usage

### Endpoint

POST `/api-password/validate`

Base URL:

    http://localhost:8080

### Valid Password (curl)

    curl -X POST http://localhost:8080/api-password/validate   -H "Content-Type: application/json"   -d '{"password":"AbTp9!fok"}'

Expected response:

    {
      "isValid": true
    }

### Invalid Password (curl)

    curl -X POST http://localhost:8080/api-password/validate   -H "Content-Type: application/json"   -d '{"password":"AbTp9!foo"}'

Expected response:

    {
      "isValid": false
    }

### Testing via Insomnia

1.  Create a new POST request.
2.  URL: `http://localhost:8080/api-password/validate`
3.  Add header: `Content-Type: application/json`
4.  Body (JSON):

    {
      "password": "AbTp9!fok"
    }

5.  Click Send.

------------------------------------------------------------------------

## 7. HTTP Status Codes

Status Code                 Description
  --------------------------- -------------------------------------------
- 200 OK                      Password validation executed successfully
- 400 Bad Request             Request body is null or malformed
- 500 Internal Server Error   Unexpected server error

------------------------------------------------------------------------

## 8. Observability

Logging is implemented at the service layer:

-   The password value is never logged
-   Only the failed rule name is recorded
-   Validation failures are logged at warn level
-   Domain layer remains framework-agnostic

------------------------------------------------------------------------

## 9. Security Considerations

-   The API does not expose internal validation rule details.
-   Only a boolean result is returned.
-   Sensitive input (password) is never written to logs.
-   Input is defensively validated at the controller level.

Potential production enhancements:

-   Rate limiting
-   Structured logging
-   HTTPS enforcement
-   Request size limitation

------------------------------------------------------------------------

## 10. Technology Stack

-   Java 21 (LTS)
-   Spring Boot
-   Maven
-   JUnit 5
-   Lombok

------------------------------------------------------------------------

## 11. Java Version Choice

The project uses Java 21 (LTS).

Although Java 17 would be sufficient for this use case, Java 21 was
selected strategically due to:

-   Long Term Support stability
-   Alignment with modern Spring Boot ecosystem
-   Improved JVM performance
-   Availability of virtual threads (Project Loom) for future
    scalability improvements

------------------------------------------------------------------------

## 12. Testing Strategy

The project includes:

-   Unit tests for each rule
-   Composite policy tests
-   Use case tests
-   Controller tests
-   Integration tests

The goal is to ensure behavioral isolation and prevent regressions when
adding new rules.

------------------------------------------------------------------------

## 13. How to Run

### Requirements

-   Java 21+
-   Maven 3.9+

### Build

    mvn clean install

### Run

    mvn spring-boot:run

Application runs at:

    http://localhost:8080

------------------------------------------------------------------------

## License

MIT License
