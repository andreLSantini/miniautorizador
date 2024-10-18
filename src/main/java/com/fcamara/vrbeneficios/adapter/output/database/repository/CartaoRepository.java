package com.fcamara.vrbeneficios.adapter.output.database.repository;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao,String> {
    boolean existsByNumeroCartao(String numeroCartao);
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
