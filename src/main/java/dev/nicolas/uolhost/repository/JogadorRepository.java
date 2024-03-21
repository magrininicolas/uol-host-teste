package dev.nicolas.uolhost.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.nicolas.uolhost.models.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    List<Jogador> findAllCodinomeByGrupo(String grupo);

    Optional<Jogador> findByEmail(String email);

}
