package br.com.clrf.web.dto;

import jakarta.validation.constraints.NotNull;

public record PasswordRequest(
        @NotNull(message = "Password cannot be null")String password) {}
