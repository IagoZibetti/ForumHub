package com.forumhub.dto;

import com.forumhub.model.Topico;

import java.time.LocalDateTime;

public record TopicoResponse(
    Long id,
    String titulo,
    String mensagem,
    LocalDateTime dataCriacao,
    String status,
    String autor,
    String curso
) {
    public TopicoResponse(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensagem(),
            topico.getDataCriacao(),
            topico.getStatus().toString(),
            topico.getAutor().getNome(),
            topico.getCurso().getNome()
        );
    }
}
