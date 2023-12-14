package com.slandrade.begins.vagas.domain.services.impl;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoJaInscritoException;
import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.repositories.CandidatoRepository;
import com.slandrade.begins.vagas.domain.repositories.CandidaturaRepository;
import com.slandrade.begins.vagas.domain.repositories.VagaRepository;
import com.slandrade.begins.vagas.domain.services.CandidaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CandidaturaServiceImpl implements CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VagaRepository vagaRepository;

    @Override
    public void postCandidatura(CandidaturaModel candidaturaModel)
            throws CandidatoJaInscritoException, CandidatoNaoEncontradoException, VagaNaoEncontradaException {

        var candidato = candidatoRepository.findById(candidaturaModel.getCandidato().getIdCandidato());
        var vaga = vagaRepository.findById(candidaturaModel.getVaga().getIdVaga());

        if (candidato.isEmpty() || vaga.isEmpty()) {
            throw new CandidatoNaoEncontradoException("Candidato ou vaga não encontrados");
        }
        if (vaga.get().getCandidaturas() == null) {
            vaga.get().setCandidaturas(new ArrayList<>());
        }

        var idCandidato = candidato.get().getIdCandidato();

        if (vaga.get().getCandidaturas()
                .stream()
                .anyMatch(c -> c.getCandidato()
                        .getIdCandidato()
                        .equals(idCandidato))) {

            throw new CandidatoJaInscritoException("Candidato já se candidatou a esta vaga");
        }

        if (vaga.get().getStatusVaga() == StatusEnum.INATIVO) {
            throw new VagaNaoEncontradaException("Vaga não encontrada");
        }
        var candidatura = CandidaturaModel.builder()
                .candidato(candidato.get())
                .vaga(vaga.get())
                .build();

        vaga.get().getCandidaturas().add(candidatura);

        candidaturaRepository.save(candidatura);

        vagaRepository.save(vaga.get());
    }
}