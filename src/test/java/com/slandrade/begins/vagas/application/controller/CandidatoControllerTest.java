package com.slandrade.begins.vagas.application.controller;

import com.slandrade.begins.vagas.domain.models.dto.CandidatoDTO;
import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import com.slandrade.begins.vagas.domain.services.CandidatoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidatoControllerTest {

    private static final UUID ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CandidatoController candidatoController;

    @Test
    void testGetCandidatos() throws Exception {

        var candidato1 = criarCandidatoDTOPadrao();
        var candidato2 = criarCandidatoDTOPadrao();

        var candidatos = Arrays.asList(candidato1, candidato2);

        when(candidatoService.getCandidatos(any(Pageable.class))).thenReturn(candidatos);

        var response = candidatoController.getCandidatos(0, 20);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(candidatos, response.getBody())
        );
    }

    @Test
    void testPostCandidato() throws Exception {

        var candidatoModel = criarCandidatoModelPadrao();
        var candidatoDTO = criarCandidatoDTOPadrao();

        when(candidatoService.postCandidato(candidatoModel)).thenReturn(candidatoDTO);

        var response = candidatoController.postCandidato(candidatoModel);

        assertAll(
                () -> verify(candidatoService, times(1)).postCandidato(candidatoModel),
                () -> assertEquals(candidatoDTO, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    void testUpdateCandidato() throws Exception {

        var candidatoModel = criarCandidatoModelPadrao();
        var expectedCandidatoDTO = criarCandidatoDTOPadrao();

        when(candidatoService.updateCandidato(candidatoModel, ID)).thenReturn(expectedCandidatoDTO);

        var response = candidatoController.updateCandidato(candidatoModel, ID);
        var returnedCandidatoDTO = response.getBody();

        assertAll(
                () -> verify(candidatoService, times(1)).updateCandidato(candidatoModel, ID),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(expectedCandidatoDTO, returnedCandidatoDTO)
        );
    }

    @Test
    void testDeleteCandidato() throws Exception {

        var response = candidatoController.deleteCandidato(ID);

        assertAll(
                () -> verify(candidatoService, times(1)).deleteCandidato(ID),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    void testDeleteCandidatos() {

        var response = candidatoController.deleteCandidatos();

        assertAll(
                () -> verify(candidatoService, times(1)).deleteCandidatos(),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    private CandidatoModel criarCandidatoModelPadrao() {
        return CandidatoModel.builder()
                .cpf("12345678900")
                .email("email@dominio.com")
                .rg("123456789")
                .telefone("123456789")
                .nome("Teste")
                .experiencia("Teste")
                .build();
    }

    private CandidatoDTO criarCandidatoDTOPadrao() {
        return CandidatoDTO.builder()
                .build();
    }
}