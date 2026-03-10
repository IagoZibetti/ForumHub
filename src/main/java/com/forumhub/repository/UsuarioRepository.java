package com.forumhub.repository;

import com.forumhub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
    Optional<Usuario> findUserByLogin(String login);
    boolean existsByLogin(String login);
}
