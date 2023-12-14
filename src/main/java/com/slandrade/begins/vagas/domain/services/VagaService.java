package com.slandrade.begins.vagas.domain.services;

import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.dto.VagaDTO;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface VagaService {
    VagaDTO postVaga(VagaModel vagaModel);
    List<VagaDTO> getVagas(Pageable pageable) throws VagaNaoEncontradaException;
    VagaDTO updateVaga(VagaModel vagaModel, UUID id) throws VagaNaoEncontradaException;
    void deleteVaga(UUID id) throws VagaNaoEncontradaException;
    void deleteVagas();
    void inativarVaga(UUID id) throws VagaNaoEncontradaException;
    void reativarVaga(UUID id) throws VagaNaoEncontradaException;
}