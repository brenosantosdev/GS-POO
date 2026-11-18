package br.fiap.breno562584.service;

import br.fiap.breno562584.domain.Bairro;
import br.fiap.breno562584.domain.Leitura;
import br.fiap.breno562584.dto.PainelDTO;
import br.fiap.breno562584.dto.PainelItemDTO;
import br.fiap.breno562584.repository.LeituraRepository;
import br.fiap.breno562584.repository.TrechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PainelService {
    private final BairroService bairroService;
    private final TrechoRepository trechoRepository;
    private final LeituraRepository leituraRepository;

    public PainelService(BairroService bairroService,
                         TrechoRepository trechoRepository,
                         LeituraRepository leituraRepository) {
        this.bairroService = bairroService;
        this.trechoRepository = trechoRepository;
        this.leituraRepository = leituraRepository;
    }

    @Transactional(readOnly = true)
    public PainelDTO montarPainel(Long bairroId) {
        Bairro bairro = bairroService.buscarEntidade(bairroId);

        List<PainelItemDTO> itens = trechoRepository
                .findByBairroIdOrderByLogradouroAsc(bairroId).stream()
                .map(trecho -> {
                    Optional<Leitura> ultima =
                            leituraRepository.findFirstByTrechoIdOrderByDataHoraDesc(trecho.getId());
                    return new PainelItemDTO(
                            trecho.getLogradouro(),
                            trecho.getTipoCalcada(),
                            trecho.getAltitude(),
                            ultima.map(Leitura::getLaminaCm).orElse(null),
                            ultima.map(Leitura::getStatus).orElse(null));
                })
                .toList();

        int normal = (int) itens.stream().filter(i -> "NORMAL".equals(i.getUltimoStatus())).count();
        int atencao = (int) itens.stream().filter(i -> "ATENCAO".equals(i.getUltimoStatus())).count();
        int alerta = (int) itens.stream().filter(i -> "ALERTA".equals(i.getUltimoStatus())).count();

        return new PainelDTO(bairro.getNome(), normal, atencao, alerta, itens);
    }
}