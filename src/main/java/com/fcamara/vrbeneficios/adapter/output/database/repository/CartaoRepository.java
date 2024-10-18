package com.fcamara.vrbeneficios.adapter.output.database.repository;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao,String> {
}
