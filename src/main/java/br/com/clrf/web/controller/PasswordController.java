package br.com.clrf.web.controller;

import br.com.clrf.service.PasswordUseCase;
import br.com.clrf.web.dto.PasswordRequest;
import br.com.clrf.web.dto.PasswordResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// # CAMADA DE INTERFACE (ENTRADA) : COMUNICACAO ENTRE O MUNDO EXTERNO E A APLICACAO. OU SEJA,
// ELE CONVERT UMA REQUISICAO HTTP EXTERNA EM UMA CHAMADA INTERNA AO CASO DE USO DA APLICACAO.
// RESUMO: controller recebe requisição HTTP, delega processamento ao caso de uso e retorna resposta ao cliente.

@RestController  // #1 : FAZ 3 COISAS: Registra como bean, Marca como controller web (classe responsável por receber requisições HTTP e devolver respostas HTTP) e faz metodos retornarem JSON.
@RequestMapping("/api-password") // #2: Define o caminho base e define regras gerais de mapeamento.
@RequiredArgsConstructor // #3 : gera automaticamente um CONSTRUTOR com todos os atributos, permitindo injeção de dependencia sem escrever construtor manualmente.
// O PasswodController é um adaptador de entrada : recebe HTTP, Converte em JSON, Chama o UseCase, Converte resposta em JSON, Retorna HTTP
// Ele adapta o mundo externo para dentro da aplicação, mas não deve conter lógica de negócio, apenas orquestração.
public class PasswordController {  // #4 : a classe precisa ser publica para que o spring consiga detectá-lo e gerencia-lo corretamente como bean web

    private final PasswordUseCase useCase;  // #5 : é PRIVATE porque impede acesso externo direto, mantém encapsulamento, só o controller usa
                                            // #5 : é FINAL para que a dependencia nao seja alterada depois de injetada, evita reatribuição, mantém onjeto consistente.
                                            // 5# : é a injeção de dependência do PasswordUseCase que o controller precisa para executar a regra de negocio.
                                            // 5# : é injetado porque o adaptador depende da porta para executar o fluxo da aplicação.

    @PostMapping("/validate") //Define que o endpoint só responde a requisições POST e o caminho é /validate, resultando no endpoint completo /api-password/validate
    public ResponseEntity<PasswordResponse> validatePassword(@Valid @RequestBody PasswordRequest request) {
        //#6 : é PUBLIC : o metodo pode ser acessado pelo Spring
        //#6 : Response<PasswordResponse> : o metodo retorna um objeto PasswordResponse encapsulado em um ResponseEntity permitindo controlar o status HTTP, headers e corpo da resposta.
        //#6 : validadePassword : nome do metodo que representa a ação de validar a senha, e será executado quando o endpoint for chamado.
        //#6 : @Valid : executa validação do DTO antes de entrar no metodo, se a senha for nula, o Spring automaticamente retorna um erro 400 Bad Request com a mensagem definida na anotação @NotNull do PasswordRequest.
        //#6 : @RequestBody : pega o JSON do corpo da requisicao, converte para um objetido PasswordRequest , armazena na variável request, e disponibiliza para uso dentro do metodo.
        boolean isValid = useCase.execute(request.password()); // #7 : variável booleana isValid armazena o resultado do execute e depois esse valor é usado para criar o DTO de saída.
        return ResponseEntity.ok(new PasswordResponse(isValid)); // #8 : Cria o DTO de saída como resultado e retorna uma resposta HTTP 200 para o cliente, com o corpo contendo o resultado da validação da senha.
    }
}

/*
    Camada web : PasswordController (adapter de entrada)

    Exponha o endpoint HTTP POST /api-password/validate por meio da classe
    PasswordController, receba a requisição contendo a senha, valide
    estruturalmente o payload via Bean Validation, delegue a execução da
    regra ao PasswordUseCase e retorne um PasswordResponse contendo
    exclusivamente o resultado booleano da validação,
    mantendo o controller restrito à orquestração e sem incorporar lógica de domínio.
 */

/*
    #1
    @RestController é uma anotação do Spring que combina @Controller e @ResponseBody.
    Ela indica que a classe é um controlador web e que os métodos retornam objetos
    que devem ser convertidos em JSON (ou outro formato) e enviados como resposta HTTP.
    Isso simplifica a criação de APIs RESTful, pois não é necessário anotar
    cada metodo com @ResponseBody.
 */

/*
    #2
    @RequestMapping("/api-password") é uma anotação do Spring que define o caminho base para os endpoints dentro da classe.
    Isso significa que todos os métodos dentro do PasswordController responderão a URLs que começam com /api-password.
    Por exemplo, se um metodo tiver @PostMapping("/validate"), o endpoint completo para esse metodo será /api-password/validate.
 */

/*
    #3
    Boilerplate : código repetitivo, previsível e estrutural que não agrega regra de negócio, mas precisa existir para a aplicação funcionar.
    Exemplo: construtores, getters, setters, equals, hashCode, toString, etc.
 */

/*
    #5
    Injeção de dependência é quando um objeto recebe suas dependências de fora (de outra classe ou framework),
    em vez de criá-las internamente.
    Aqui o controller não cria uma instância, ele recebe pronto, quem fornece é o Spring que gerencia e fornece o PasswordUseCase.
 */

/*
    #6
    PAYLOAD : conteúdo de dados enviado no corpo de uma requisição ou resposta HTTP, geralmente em formato JSON ou XML.
    Ou seja, não é a URL, não é o header, é o corpo (body) da requisição ou resposta.
 */