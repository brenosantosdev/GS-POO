package br.fiap.breno562584.mapper;

import br.fiap.breno562584.domain.Leitura;
import br.fiap.breno562584.domain.Trecho;
import br.fiap.breno562584.dto.LeituraDTO;
import org.springframework.stereotype.Component;

@Component
public class LeituraMapper {

    public Leitura toEntity(LeituraDTO dto, Trecho trecho, String status) {
        Leitura leitura = new Leitura();
        leitura.setId(dto.getId());
        leitura.setDataHora(dto.getDataHora());
        leitura.setLaminaCm(dto.getLaminaCm());
        leitura.setStatus(status);
        leitura.setTrecho(trecho);
        return leitura;
    }

    public LeituraDTO toDTO(Leitura leitura) {
        LeituraDTO dto = new LeituraDTO();
        dto.setId(leitura.getId());
        dto.setTrechoId(leitura.getTrecho().getId());
        dto.setDataHora(leitura.getDataHora());
        dto.setLaminaCm(leitura.getLaminaCm());
        dto.setStatus(leitura.getStatus());
        dto.setTrechoDescricao(leitura.getTrecho().getLogradouro());
        return dto;
    }
}