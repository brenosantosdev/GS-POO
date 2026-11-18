package br.fiap.breno562584.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LeituraDTO {
    private Long id;

    @NotNull(message = "Escolha o trecho")
    private Long trechoId;

    @NotNull(message = "Informe a data e a hora")
    @PastOrPresent(message = "A data e a hora não podem estar no futuro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHora;

    @NotNull(message = "Informe a lâmina de água")
    @Min(value = 0, message = "A lâmina deve ser no mínimo 0 cm")
    @Max(value = 300, message = "A lâmina deve ser no máximo 300 cm")
    private Integer laminaCm;

    private String status;
    private String trechoDescricao;
}