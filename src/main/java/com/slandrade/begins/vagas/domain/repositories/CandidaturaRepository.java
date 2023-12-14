package com.slandrade.begins.vagas.domain.repositories;

import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidaturaRepository extends JpaRepository<CandidaturaModel, UUID> {
}