package br.fiap.breno562584.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "GS234_LEITURA")
@Getter
@Setter
@NoArgsConstructor
public class Leitura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "LAMINA_CM", nullable = false)
    private Integer laminaCm;

    @Column(nullable = false, length = 10)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRECHO_ID", nullable = false)
    private Trecho trecho;
}