package com.slandrade.begins.vagas.domain.repositories;

import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CandidatoRepository extends JpaRepository<CandidatoModel, UUID> {

    @Query("SELECT c FROM CandidatoModel c WHERE c.email = :email OR c.cpf = :cpf")
    Optional<CandidatoModel> findByEmailOrCpf(@Param("email") String email, @Param("cpf") String cpf);

}