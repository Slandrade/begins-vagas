package com.slandrade.begins.vagas.application.controller;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;
import com.slandrade.begins.vagas.domain.services.CandidaturaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class CandidaturaControllerTest {

    @Mock
    private CandidaturaService candidaturaService;

    @InjectMocks
    private CandidaturaController candidaturaController;

    @Test
    void testCadastrarSuccess() throws Exception {

        var candidaturaModel = new CandidaturaModel();

        doNothing().when(candidaturaService).postCandidatura(candidaturaModel);

        var response = candidaturaController.cadastrar(candidaturaModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCadastrarFailure() throws Exception {

        var candidaturaModel = new CandidaturaModel();

        doThrow(new CandidatoNaoEncontradoException("Candidato ou vaga não encontrados")).when(candidaturaService)
                .postCandidatura(candidaturaModel);

        assertThrows(CandidatoNaoEncontradoException.class, () -> candidaturaController.cadastrar(candidaturaModel));
    }
}