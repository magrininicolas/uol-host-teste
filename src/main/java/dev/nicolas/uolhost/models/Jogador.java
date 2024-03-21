package dev.nicolas.uolhost.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_jogadores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome do jogador não pode estar vazio")
    private String nome;

    @NotEmpty(message = "O email do jogador não pode estar vazio")
    @Email(message = "Digite o email corretamente. Ex.: email@email.com")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "O telefone do jogador não pode estar vazio.")
    @NotBlank(message = "O telefone do jogador não pode estar vazio.")
    @Pattern(regexp = "(\\(\\d{2}\\)\\d{4,5}-\\d{4})", message = "O telefone do jogador deve estar no padrão (99)99999-9999")
    private String telefone;

    private String codinome;

    private String grupo;

    public Jogador(Jogador jogador) {
        this.id = jogador.getId();
        this.nome = jogador.getNome();
        this.email = jogador.getEmail();
        this.telefone = jogador.getTelefone();
        this.codinome = jogador.getCodinome();
        this.grupo = jogador.getGrupo();
    }
}
