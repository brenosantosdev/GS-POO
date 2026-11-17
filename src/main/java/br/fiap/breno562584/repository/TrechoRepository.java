package br.fiap.breno562584.repository;

import br.fiap.breno562584.domain.Trecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrechoRepository extends JpaRepository<Trecho, Long> {
    List<Trecho> findAllByOrderByBairroNomeAscLogradouroAsc();
    List<Trecho> findByBairroIdOrderByLogradouroAsc(Long bairroId);
    boolean existsByBairroId(Long bairroId);
}