package com.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicoCreateRequest(
    @NotBlank(message = "O Título é obrigatório")
    String titulo,
    
    @NotBlank(message = "A Mensagem é obrigatória")
    String mensagem,
    
    @NotBlank(message = "O Nome do curso é obrigatório")
    String nomeCurso
) {}
