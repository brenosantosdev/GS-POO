package br.fiap.breno562584.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GS234_BAIRRO")
@Getter
@Setter
@NoArgsConstructor
public class Bairro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 10)
    private String zona;

    @Column(name = "POSSUI_PISCINAO")
    private Boolean possuiPiscinao;
}