package com.forumhub.controller;

import com.forumhub.dto.TopicoCreateRequest;
import com.forumhub.dto.TopicoResponse;
import com.forumhub.dto.TopicoUpdateRequest;
import com.forumhub.model.Topico;
import com.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    
    @Autowired
    private TopicoService topicService;
    
    // Cria novo tópico somente com autenticação verificada
    @PostMapping
    public ResponseEntity<TopicoResponse> createTopico(
            @RequestBody @Valid TopicoCreateRequest request,
            Authentication authentication) {
        
        String username = authentication.getName();
        Topico topico = topicService.createTopico(request, username);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TopicoResponse(topico));
    }
    
    // Faz a lista de todos os tópicos sem necessidade de autenticar
    @GetMapping
    public ResponseEntity<List<TopicoResponse>> getAllTopicos() {
        List<Topico> topics = topicService.getAllTopicos();
        
        List<TopicoResponse> response = topics.stream()
                .map(TopicoResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    // Busca um tópico pelo ID sem autenticar
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> getTopicoById(@PathVariable Long id) {
        Topico topico = topicService.getTopicoById(id);
        return ResponseEntity.ok(new TopicoResponse(topico));
    }
    
    // Atualiza um tópico somente se for o autor e estiver autenticado
    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> updateTopico(
            @PathVariable Long id,
            @RequestBody TopicoUpdateRequest request,
            Authentication authentication) {
        
        String username = authentication.getName();
        Topico topico = topicService.updateTopico(id, request, username);
        
        return ResponseEntity.ok(new TopicoResponse(topico));
    }
    
    // Deleta um tópico somente se for o autor e estiver autenticado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(
            @PathVariable Long id,
            Authentication authentication) {
        
        String username = authentication.getName();
        topicService.deleteTopico(id, username);
        
        return ResponseEntity.ok().build();
    }
}
