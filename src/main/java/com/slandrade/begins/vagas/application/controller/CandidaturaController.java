package com.slandrade.begins.vagas.application.controller;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoJaInscritoException;
import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;
import com.slandrade.begins.vagas.domain.services.CandidaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candidaturas")
public class CandidaturaController {

    private final CandidaturaService candidaturaService;

    @PostMapping("/")
    public ResponseEntity<Object> cadastrar(@RequestBody CandidaturaModel candidaturaModel)
            throws CandidatoJaInscritoException, CandidatoNaoEncontradoException, VagaNaoEncontradaException {

            candidaturaService.postCandidatura(candidaturaModel);
            return ResponseEntity.ok().build();
    }
}