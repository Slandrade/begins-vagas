package com.slandrade.begins.vagas.domain.services;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoJaInscritoException;
import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;

public interface CandidaturaService {
    void postCandidatura(CandidaturaModel candidaturaModel) throws CandidatoJaInscritoException, CandidatoNaoEncontradoException, VagaNaoEncontradaException;
}