package com.slandrade.begins.vagas.domain.repositories;

import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VagaRepository extends JpaRepository<VagaModel, UUID> {
}