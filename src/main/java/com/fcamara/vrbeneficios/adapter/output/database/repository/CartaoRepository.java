package com.fcamara.vrbeneficios.adapter.output.database.repository;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao,String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByNumeroCartao(String numeroCartao);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
