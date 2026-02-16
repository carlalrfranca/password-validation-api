package br.com.clrf.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordControllerIT {

    private static final String ENDPOINT = "/api-password/validate";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturn200AndIsValidTrueWhenPasswordIsValid() throws Exception {
        String body = "{\"password\":\"AbTp9!fok\"}";
        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .accept(JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$.isValid").value(true));
    }

    @Test
    void shouldReturn200AndIsValidFalseWhenPasswordIsInvalidBusinessRule() throws Exception {
        String body = "{\"password\":\"AbTp9 fok\"}";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .accept(JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$.isValid").value(false));
    }

    @Test
    void shouldReturn200AndIsValidFalseWhenPasswordIsEmpty() throws Exception {
        String body = "{\"password\":\"\"}";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .accept(JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid").value(false));
    }

    @Test
    void shouldReturn400WhenRequestBodyIsMissing() throws Exception {
        String body = "{}";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .accept(JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenPasswordIsNull() throws Exception {
        String body = "{\"password\":null}";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(JSON)
                        .accept(JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn415WhenContentTypeIsNotJson() throws Exception {
        String body = "{\"password\":\"AbTp9!fok\"}";

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(JSON)
                        .content(body))
                .andExpect(status().isUnsupportedMediaType());
    }
}