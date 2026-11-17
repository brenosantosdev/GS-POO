package br.fiap.breno562584.repository;

import br.fiap.breno562584.domain.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
    List<Leitura> findAllByOrderByDataHoraDesc();
    Optional<Leitura> findFirstByTrechoIdOrderByDataHoraDesc(Long trechoId);
    boolean existsByTrechoId(Long trechoId);
    void deleteByTrechoId(Long trechoId);
}