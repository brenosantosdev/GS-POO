package br.fiap.breno562584.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PainelDTO {
    private String bairroNome;
    private int qtdNormal;
    private int qtdAtencao;
    private int qtdAlerta;
    private List<PainelItemDTO> itens;
}