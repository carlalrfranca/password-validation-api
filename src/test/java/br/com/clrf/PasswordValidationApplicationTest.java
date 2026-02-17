package br.com.clrf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordValidationApplicationTest {

    @Test
    void shouldRunMain() {
        PasswordValidationApplication.main(new String[]{});
    }
}
