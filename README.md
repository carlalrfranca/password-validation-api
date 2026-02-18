# Password Validation API

Secure password validation API built with Spring Boot, following layered
architecture and SOLID principles.

---

## 1. Overview

This project exposes a REST API responsible for validating passwords according to a strict \
and deterministic set of security rules.

The implementation prioritizes deterministic behavior and explicit rule modeling over \
implicit validation logic.
All validation rules are modeled as first-class domain components to ensure clarity, \
predictability, and extensibility.\

The solution was designed with an emphasis on:

- Separation of concerns
- Low coupling and high cohesion
- Extensibility (Open/Closed Principle)
- Testability
- Clean and explicit domain modeling

---

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
-   Contains only alphanumeric characters and the explicitly defined special character set.

The API returns a boolean indicating whether the password is valid.

---

## 3. Project Structure

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

The domain layer is framework-agnostic and contains only pure business logic.\
The domain layer is intentionally isolated from Spring and any framework-specific \ 
annotations to preserve portability and maintain strict separation of concerns.

---

## 4. Architectural Patterns

### Layered Architecture

The project separates responsibilities into distinct layers:

- **web** → HTTP concerns\
- **service** → application orchestration\
- **domain** → business rules\
- **config** → dependency composition

This protects the domain from framework dependencies and keeps coupling low.

---

### Composite Pattern for Rule Composition

Password validation is implemented using a Composite structure.

Each rule implements:

```java
boolean isSatisfiedBy(String password);
```

`CompositePasswordPolicy` aggregates multiple rules and evaluates them sequentially.

Benefits:

- Uniform treatment of single and multiple rules
- Extension without modifying existing validation logic
- Isolated rule-level testing
- Reduced conditional complexity

---

## 5. Validation Strategy

- Validation is delegated to CompositePasswordPolicy, which executes all configured rules and stops at the first failure (fail-fast approach).
- The domain layer assumes valid (non-null) input as a precondition.
- Transport-level validation (malformed JSON, null body, null fields) is handled at the web layer.
---

## 6. Error Handling & Responsibility Matrix

The system explicitly separates transport errors from business rule violations.


### Transport-Level Errors (Web Layer)

Handled automatically by Spring MVC and Jackson:

- Malformed JSON → 400 Bad Request
- Missing request body → 400 Bad Request
- Null password field → 400 Bad Request
- Incorrect content type → 400 Bad Request

These errors are considered contract violations, not business failures.\
The controller ensures invalid requests do not reach the domain layer.\

---

### Application Layer (Service)

The service layer:

- Orchestrates validation
- Logs failed rule names
- Does not perform transport validation
- Does not contain business rules

___

### Domain Layer (Policy & Rules)

The domain layer:

- Evaluates password rules
- Returns validation result
- Does not handle JSON parsing
- Does not handle HTTP concerns
- Assumes input respects application preconditions

This separation ensures the domain remains pure and framework-agnostic.

--- 

## 7. API Usage
### Endpoint

POST `/api-password/validate`

Base URL:

```
http://localhost:8080
```

### Valid Password (curl)

```
curl -X POST http://localhost:8080/api-password/validate \
  -H "Content-Type: application/json" \
  -d '{"password":"AbTp9!fok"}'
```

Expected response:

```
{
  "isValid": true
}
```

### Invalid Password (curl)

```
curl -X POST http://localhost:8080/api-password/validate \
  -H "Content-Type: application/json" \
  -d '{"password":"AbTp9!foo"}'
```

Expected response:

```
{
  "isValid": false
}
```

### Testing via Insomnia

1.  Create a new POST request.
2.  URL: `http://localhost:8080/api-password/validate`
3.  Add header: `Content-Type: application/json`
4.  Body (JSON):

```
{
  "password": "AbTp9!fok"
}
```

5. Click Send.

---

## 8. HTTP Status Codes

| Status Code | Description |
|------------|-------------|
| 200 OK | Password validation executed successfully |
| 400 Bad Request | Request violates HTTP contract (null body, malformed JSON, invalid content type) |
| 500 Internal Server Error | Unexpected server error |

---

## 9. Observability

Logging is implemented at the service layer:

- The password value is never logged
- Only the failed rule name is recorded
- Validation failures are logged at warn level
- Domain layer remains framework-agnostic

---

## 10. Security Considerations

- The API does not expose internal validation rule details.
- Only a boolean result is returned.
- Sensitive input (password) is never written to logs.
- Input is defensively validated at the controller level.

Potential production enhancements:

- Rate limiting
- Structured logging
- HTTPS enforcement
- Request size limitation

---

## 11. Technology Stack

- Java 21 (LTS)
- Spring Boot
- Maven
- JUnit 5
- Lombok

---

## 12. Java Version Choice

The project uses Java 21 (LTS).

Although Java 17 would be sufficient for this use case, Java 21 was selected strategically due to:

- Long Term Support stability
- Alignment with modern Spring Boot ecosystem
- Improved JVM performance
- Availability of virtual threads (Project Loom) for future scalability improvements

---

## 13. Testing Strategy

The test suite is structured by architectural layers:

- Unit tests for each individual rule (domain validation logic).
- Composite policy tests to ensure rule aggregation behavior.
- Use case tests to validate application orchestration.
- Controller tests to verify HTTP contract and response structure.
- Integration tests covering end-to-end flow.

Mockito was selectively applied in application and configuration tests to control specific execution paths (e.g., Optional branches) and validate behavior in isolation.

The goal is to ensure behavioral correctness, architectural separation, and prevent regressions when introducing new validation rules.

---

## 14. How to Run

### Requirements

- Java 21+
- Maven 3.9+

### Build

```
mvn clean install
```
### Run

```
mvn spring-boot:run
```
Application runs at:

```
http://localhost:8080
```
---

## License

MIT License