package br.com.clrf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//COMBINA @Configuration (permite BEAN como FONTE de configuração, @EnableAutoConfiguration (MVC e Tomcat)e @ComponentScan (busca classes anotadas para registrr como beans)
class PasswordValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasswordValidationApplication.class, args);// Por onde começar a CONFIGURAÇÃO.
    }
}

/*
    Inicialize a aplicação Spring Boot por meio da classe PasswordValidationApplication,
    delegando ao SpringApplication.run(...) a responsabilidade de inicializar o contexto
    (container do Spring), registrar os beans, aplicar a auto-configuração e expor a camada web;
    mantenha essa classe exclusivamente como ponto de entrada do sistema, sem incluir regras de
    domínio ou lógica de negócio, preservando seu papel estrutural na arquitetura.
 */

/*
    A classe main passa a si mesma como REFERÊNCIA para o metodo
    run para que o spring saiba por onde começar a CONFIGURACAO.
 */

/*
COMBINA
    Classe principal da aplicacao marcada com @SpringBootApplication que combina:
    @Configuration : permite usar @Bean dentro dela. Transforma a classe em FONTE DE CONFIGURAÇÃO.
    @EnableAutoConfiguration : para essa API ela configura automaticamente o Spring MVC e o Tomcat.
    @ComponentScan : manda o spring procurar classes anotadas para registrá-las como beans.
 */