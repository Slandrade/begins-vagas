package com.slandrade.begins.vagas.application.controller;

import com.slandrade.begins.vagas.domain.models.dto.VagaDTO;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.models.enums.TipoVagaEnum;
import com.slandrade.begins.vagas.domain.services.VagaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VagaControllerTest {

    private static final UUID ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    @InjectMocks
    private VagaController vagaController;

    @Mock
    private VagaService vagaService;

    @Test
    void testGetVagas() throws Exception {

        var vaga1 = criarVagaDTOPadrao();
        var vaga2 = criarVagaDTOPadrao();

        var vagas = Arrays.asList(vaga1, vaga2);

        when(vagaService.getVagas(any(Pageable.class))).thenReturn(vagas);

        var response = vagaController.getVagas(0, 20);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(vagas, response.getBody())
        );
    }

    @Test
    void testPostVaga() {

        var vagaModel = criarVagaModelPadrao();
        var vagaDTO = criarVagaDTOPadrao();

        when(vagaService.postVaga(vagaModel)).thenReturn(vagaDTO);

        var response = vagaController.postVaga(vagaModel);

        assertAll(
                () -> verify(vagaService, times(1)).postVaga(vagaModel),
                () -> assertEquals(vagaDTO, response.getBody()),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    void testUpdateVaga() throws Exception {

        var vagaModel = criarVagaModelPadrao();
        var vagaDTO = criarVagaDTOPadrao();

        when(vagaService.updateVaga(vagaModel, ID)).thenReturn(vagaDTO);

        var response = vagaController.updateVaga(vagaModel, ID);
        var returnedVagaDTO = response.getBody();

        assertAll(
                () -> verify(vagaService, times(1)).updateVaga(vagaModel, ID),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(vagaDTO, returnedVagaDTO)
        );
    }

    @Test
    void testDeleteVaga() throws Exception {

        var response = vagaController.deleteVaga(ID);

        assertAll(
                () -> verify(vagaService, times(1)).deleteVaga(ID),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    void testDeleteVagas() {

        var response = vagaController.deleteVagas();

        assertAll(
                () -> verify(vagaService, times(1)).deleteVagas(),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

    @Test
    void testInativarVaga() throws Exception {

        doNothing().when(vagaService).inativarVaga(ID);

        var response = vagaController.inativarVaga(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testReativarVaga() throws Exception {

        doNothing().when(vagaService).reativarVaga(ID);

        var response = vagaController.reativarVaga(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private VagaDTO criarVagaDTOPadrao() {
        return VagaDTO.builder()
                .build();
    }

    private VagaModel criarVagaModelPadrao() {
        return VagaModel.builder()
                .titulo("Desenvolvedor Java")
                .descricao("Desenvolvedor Java com experiência em Spring Boot")
                .beneficios("VR, VT, Plano de Saúde")
                .requisitos("Java, Spring Boot, JPA, Hibernate")
                .tipoVaga(TipoVagaEnum.CLT)
                .quantidadeVagas(1)
                .nivelExperiencia("Pleno")
                .dataPublicacao(LocalDateTime.now())
                .statusVaga(StatusEnum.ATIVO)
                .build();
    }
}