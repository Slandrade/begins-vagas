package com.slandrade.begins.vagas.domain.services.impl;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoJaInscritoException;
import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.models.enums.TipoVagaEnum;
import com.slandrade.begins.vagas.domain.repositories.CandidatoRepository;
import com.slandrade.begins.vagas.domain.repositories.CandidaturaRepository;
import com.slandrade.begins.vagas.domain.repositories.VagaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidaturaServiceImplTest {

    private static final UUID ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    @Mock
    private CandidaturaRepository candidaturaRepository;
    @Mock
    private CandidatoRepository candidatoRepository;
    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private CandidaturaServiceImpl candidaturaService;

    @Test
    void testPostCandidaturaSuccess() {

        var candidaturaModel = CandidaturaModel.builder()
                .candidato(criarCandidatoModelPadrao())
                .vaga(criarVagaModelPadrao())
                .build();

        when(candidatoRepository.findById(ID)).thenReturn(Optional.of(criarCandidatoModelPadrao()));
        when(vagaRepository.findById(ID)).thenReturn(Optional.of(criarVagaModelPadrao()));

        assertDoesNotThrow(() -> candidaturaService.postCandidatura(candidaturaModel));
    }

    @Test
    void testPostCandidaturaFailure() {

        var candidaturaModel = CandidaturaModel.builder()
                .candidato(criarCandidatoModelPadrao())
                .vaga(criarVagaModelPadrao())
                .build();

        when(candidatoRepository.findById(any())).thenReturn(Optional.empty());
        when(vagaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CandidatoNaoEncontradoException.class, () -> candidaturaService.postCandidatura(candidaturaModel));
    }

    @Test
    void testPostCandidaturaCandidatoJaInscrito() {

        var candidatoModel = criarCandidatoModelPadrao();
        candidatoModel.setIdCandidato(ID);

        var candidaturaExistente = CandidaturaModel.builder()
                .candidato(candidatoModel)
                .build();

        var vagaModel = criarVagaModelPadrao();
        vagaModel.setCandidaturas(List.of(candidaturaExistente));

        var novaCandidaturaModel = CandidaturaModel.builder()
                .candidato(candidatoModel)
                .vaga(vagaModel)
                .build();

        when(candidatoRepository.findById(ID)).thenReturn(Optional.of(candidatoModel));
        when(vagaRepository.findById(any())).thenReturn(Optional.of(vagaModel));

        assertThrows(CandidatoJaInscritoException.class, () -> candidaturaService.postCandidatura(novaCandidaturaModel));
    }

    @Test
    void testPostCandidaturaVagaInativa() {

        var vagaInativa = criarVagaModelPadrao();
        vagaInativa.setStatusVaga(StatusEnum.INATIVO);

        var candidaturaModel = CandidaturaModel.builder()
                .candidato(criarCandidatoModelPadrao())
                .vaga(vagaInativa)
                .build();

        when(candidatoRepository.findById(any())).thenReturn(Optional.ofNullable(criarCandidatoModelPadrao()));
        when(vagaRepository.findById(any())).thenReturn(Optional.of(vagaInativa));

        assertThrows(VagaNaoEncontradaException.class, () -> candidaturaService.postCandidatura(candidaturaModel));
    }

    private CandidatoModel criarCandidatoModelPadrao() {
        return CandidatoModel.builder()
                .idCandidato(ID)
                .cpf("12345678900")
                .email("email@dominio.com")
                .rg("123456789")
                .telefone("123456789")
                .nome("Teste")
                .experiencia("Teste")
                .build();
    }

    private VagaModel criarVagaModelPadrao() {
        return VagaModel.builder()
                .idVaga(ID)
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