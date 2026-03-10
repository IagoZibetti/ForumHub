package com.forumhub.controller;

import com.forumhub.dto.LoginRequest;
import com.forumhub.dto.RegisterRequest;
import com.forumhub.dto.RegisterResponse;
import com.forumhub.dto.TokenResponse;
import com.forumhub.model.Usuario;
import com.forumhub.repository.UserRepository;
import com.forumhub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login - Verifica e valida usuário existente e retorna token JWT
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        // Registra o token de autenticação com login e senha
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.login(), request.senha());

        // Spring valida comparando com a hash do banco
        Authentication authentication = authenticationManager.authenticate(authToken);

        // Gera o token para o usuário autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    //Registro - cadastra novo usuário com senha em formato de hash
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody @Valid RegisterRequest request) {
        // Verificar se o login já foi efetuado
        if (userRepository.existsByLogin(request.login())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Login já efetuado");
        }

        // Criar novo usuário
        Usuario newUser = new Usuario();
        newUser.setLogin(request.login());
        newUser.setNome(request.nome());
        newUser.setEmail(request.email());

        // A Hash da senha é gerada
        String hashedPassword = passwordEncoder.encode(request.senha());
        newUser.setSenha(hashedPassword);

        // Salva no banco de dados
        Usuario savedUser = userRepository.save(newUser);

        // Retorna resposta
        RegisterResponse response = new RegisterResponse(
                savedUser.getId(),
                savedUser.getLogin(),
                savedUser.getNome(),
                savedUser.getEmail(),
                "Usuário cadastrado com sucesso!"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}