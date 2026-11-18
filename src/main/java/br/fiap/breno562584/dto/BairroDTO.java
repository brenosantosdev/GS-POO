package br.fiap.breno562584.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BairroDTO {
    private Long id;

    @NotBlank(message = "Informe o nome do bairro")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @NotBlank(message = "Escolha a zona")
    private String zona;

    private Boolean possuiPiscinao;
}