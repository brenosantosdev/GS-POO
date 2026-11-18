package br.fiap.breno562584.service;

import br.fiap.breno562584.domain.Leitura;
import br.fiap.breno562584.domain.Trecho;
import br.fiap.breno562584.dto.LeituraDTO;
import br.fiap.breno562584.mapper.LeituraMapper;
import br.fiap.breno562584.repository.LeituraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeituraService {
    private static final int LIMIAR = 14;   // P2: L da equipe

    private final LeituraRepository repository;
    private final TrechoService trechoService;
    private final LeituraMapper mapper;

    public LeituraService(LeituraRepository repository,
                          TrechoService trechoService,
                          LeituraMapper mapper) {
        this.repository = repository;
        this.trechoService = trechoService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<LeituraDTO> listar() {
        return repository.findAllByOrderByDataHoraDesc().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public LeituraDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarEntidade(id));
    }

    private Leitura buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Leitura não encontrada."));
    }

    // R1: NORMAL abaixo de L/2, ATENCAO de L/2 até menos que L, ALERTA a partir de L
    public String calcularStatus(int laminaCm) {
        if (laminaCm >= LIMIAR) {
            return "ALERTA";
        }
        if (laminaCm >= LIMIAR / 2) {
            return "ATENCAO";
        }
        return "NORMAL";
    }

    @Transactional
    public LeituraDTO salvar(LeituraDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Trecho trecho = trechoService.buscarEntidade(dto.getTrechoId());
        String status = calcularStatus(dto.getLaminaCm());   // sempre recalcula
        Leitura salva = repository.save(mapper.toEntity(dto, trecho, status));
        return mapper.toDTO(salva);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }
}