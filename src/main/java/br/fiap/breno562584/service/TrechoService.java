package br.fiap.breno562584.service;

import br.fiap.breno562584.domain.Bairro;
import br.fiap.breno562584.domain.Trecho;
import br.fiap.breno562584.dto.TrechoDTO;
import br.fiap.breno562584.mapper.TrechoMapper;
import br.fiap.breno562584.repository.LeituraRepository;
import br.fiap.breno562584.repository.TrechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrechoService {
    private final TrechoRepository repository;
    private final LeituraRepository leituraRepository;
    private final BairroService bairroService;
    private final TrechoMapper mapper;

    public TrechoService(TrechoRepository repository,
                         LeituraRepository leituraRepository,
                         BairroService bairroService,
                         TrechoMapper mapper) {
        this.repository = repository;
        this.leituraRepository = leituraRepository;
        this.bairroService = bairroService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<TrechoDTO> listar() {
        return repository.findAllByOrderByBairroNomeAscLogradouroAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrechoDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarEntidade(id));
    }

    // usado pelo LeituraService
    public Trecho buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Trecho não encontrado."));
    }

    @Transactional
    public TrechoDTO salvar(TrechoDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());
        }
        Bairro bairro = bairroService.buscarEntidade(dto.getBairroId());
        Trecho salvo = repository.save(mapper.toEntity(dto, bairro));
        return mapper.toDTO(salvo);
    }

    // P5 = 1 (cascata): apaga as leituras e depois o trecho, na mesma transação
    @Transactional
    public void excluir(Long id) {
        Trecho trecho = buscarEntidade(id);
        if (leituraRepository.existsByTrechoId(id)) {
            leituraRepository.deleteByTrechoId(id);
        }
        repository.delete(trecho);
    }
}