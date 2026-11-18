package br.fiap.breno562584.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrechoDTO {
    private Long id;

    @NotBlank(message = "Informe o logradouro")
    @Size(max = 120, message = "O logradouro deve ter no máximo 120 caracteres")
    private String logradouro;

    @NotBlank(message = "Escolha o tipo de calçada")
    private String tipoCalcada;

    @NotNull(message = "Informe a altitude")
    @Min(value = 0, message = "A altitude deve ser no mínimo 0")
    @Max(value = 2000, message = "A altitude deve ser no máximo 2000")
    private Integer altitude;

    @NotNull(message = "Escolha o bairro")
    private Long bairroId;

    private String bairroNome;
}