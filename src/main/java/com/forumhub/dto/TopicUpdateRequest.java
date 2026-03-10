package com.forumhub.dto;

public record TopicoUpdateRequest(
    String titulo,
    String mensagem,
    String status
) {}
