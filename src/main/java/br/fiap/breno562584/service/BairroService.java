package br.fiap.breno562584.service;

import br.fiap.breno562584.domain.Bairro;
import br.fiap.breno562584.dto.BairroDTO;
import br.fiap.breno562584.mapper.BairroMapper;
import br.fiap.breno562584.repository.BairroRepository;
import br.fiap.breno562584.repository.TrechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BairroService {
    private final BairroRepository repository;
    private final TrechoRepository trechoRepository;
    private final BairroMapper mapper;

    public BairroService(BairroRepository repository,
                         TrechoRepository trechoRepository,
                         BairroMapper mapper) {
        this.repository = repository;
        this.trechoRepository = trechoRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<BairroDTO> listar() {
        return repository.findAllByOrderByNomeAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public BairroDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarEntidade(id));
    }

    // usado por outros services (o TrechoService pede o Bairro aqui)
    public Bairro buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Bairro não encontrado."));
    }

    @Transactional
    public BairroDTO salvar(BairroDTO dto) {
        if (dto.getId() != null) {
            buscarEntidade(dto.getId());   // alteração: o bairro precisa existir
        }
        Bairro salvo = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        Bairro bairro = buscarEntidade(id);
        if (trechoRepository.existsByBairroId(id)) {
            throw new RegraNegocioException(
                    "Não é possível excluir o bairro, pois ele possui trechos cadastrados.");
        }
        repository.delete(bairro);
    }
}