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

