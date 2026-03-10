package com.forumhub.service;

import com.forumhub.dto.TopicoCreateRequest;
import com.forumhub.dto.TopicoUpdateRequest;
import com.forumhub.model.Curso;
import com.forumhub.model.Topico;
import com.forumhub.model.Usuario;
import com.forumhub.repository.CursoRepository;
import com.forumhub.repository.TopicoRepository;
import com.forumhub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicoService {
    
    @Autowired
    private TopicoRepository topicRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Transactional
    public Topico createTopico(TopicoCreateRequest request, String username) {
        // Validar tópico duplicado
        if (topicRepository.existsByTituloAndMensagem(request.titulo(), request.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com este título e mensagem");
        }
        
        // Buscar usuário autor
        Usuario autor = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        // Buscar ou criar curso
        Curso curso = cursoRepository.findByNomeIgnoreCase(request.nomeCurso())
                .orElseGet(() -> {
                    Curso newCurso = new Curso();
                    newCurso.setNome(request.nomeCurso());
                    newCurso.setCategoria("Programação");
                    return cursoRepository.save(newCurso);
                });
        
        // Criar tópico
        Topico topico = new Topico();
        topico.setTitulo(request.titulo());
        topico.setMensagem(request.mensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);
        
        return topicRepository.save(topico);
    }
    
    public List<Topico> getAllTopicos() {
        return topicRepository.findAll();
    }
    
    public Topico getTopicoById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
    }
    
    @Transactional
    public Topico updateTopico(Long id, TopicoUpdateRequest request, String username) {
        Topico topico = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        
        // Verificar se usuário é o autor
        if (!topico.getAutor().getLogin().equals(username)) {
            throw new IllegalArgumentException("Apenas o autor pode atualizar o tópico");
        }
        
        // Atualizar campos
        if (request.titulo() != null && !request.titulo().isBlank()) {
            topico.setTitulo(request.titulo());
        }
        
        if (request.mensagem() != null && !request.mensagem().isBlank()) {
            topico.setMensagem(request.mensagem());
        }
        
        if (request.status() != null && !request.status().isBlank()) {
            try {
                Topico.StatusTopicoo newStatus = Topico.StatusTopicoo.valueOf(request.status().toUpperCase());
                topico.setStatus(newStatus);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + request.status());
            }
        }
        
        return topicRepository.save(topico);
    }
    
    @Transactional
    public void deleteTopico(Long id, String username) {
        Topico topico = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        
        // Verificar se usuário é o autor
        if (!topico.getAutor().getLogin().equals(username)) {
            throw new IllegalArgumentException("Apenas o autor pode deletar o tópico");
        }
        
        topicRepository.delete(topico);
    }
}
