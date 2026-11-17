package br.fiap.breno562584.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GS234_TRECHO")
@Getter
@Setter
@NoArgsConstructor
public class Trecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String logradouro;

    @Column(name = "TIPO_CALCADA", nullable = false, length = 20)
    private String tipoCalcada;

    @Column(nullable = false)
    private Integer altitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BAIRRO_ID", nullable = false)
    private Bairro bairro;
}