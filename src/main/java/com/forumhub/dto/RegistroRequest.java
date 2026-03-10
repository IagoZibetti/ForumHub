package com.forumhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        String senha,

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @Email(message = "O Email digitado foi inválido")
        @NotBlank(message = "O Email é um campo obrigatório")
        String email
) {}