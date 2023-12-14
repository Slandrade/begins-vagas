package com.slandrade.begins.vagas.domain.services;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.EmailOuCpfJaCadastradoException;
import com.slandrade.begins.vagas.domain.models.dto.CandidatoDTO;
import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CandidatoService {
    CandidatoDTO postCandidato(CandidatoModel candidatoModel) throws EmailOuCpfJaCadastradoException;
    List<CandidatoDTO> getCandidatos(Pageable pageable) throws CandidatoNaoEncontradoException;
    CandidatoDTO updateCandidato(CandidatoModel candidatoModel, UUID id) throws CandidatoNaoEncontradoException;
    void deleteCandidato(UUID id) throws CandidatoNaoEncontradoException;
    void deleteCandidatos();
}