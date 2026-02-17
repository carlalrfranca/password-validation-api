package br.com.clrf.web.controller;

import br.com.clrf.service.PasswordUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PasswordController.class)
class PasswordControllerTest {

    private static final String ENDPOINT = "/api-password/validate";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordUseCase passwordUseCase;

    private String validBody;
    private String invalidBody;

    @BeforeEach
    void setUp() {
        validBody = "{\"password\":\"AbTp9!fok\"}";
        invalidBody = "{\"password\":\"AbTp9 fok\"}";
    }

    @Test
    void shouldReturn200AndValidTrue_WhenUseCaseReturnsTrue() throws Exception {
        Mockito.when(passwordUseCase.execute("AbTp9!fok"))
                .thenReturn(true);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .content(validBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$.isValid").value(true));
    }

    @Test
    void shouldReturn200AndValidFalse_WhenUseCaseReturnsFalse() throws Exception {
        Mockito.when(passwordUseCase.execute("AbTp9 fok"))
                .thenReturn(false);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .content(invalidBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$.isValid").value(false));
    }
}