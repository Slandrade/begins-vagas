package com.slandrade.begins.vagas.domain.services.impl;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.EmailOuCpfJaCadastradoException;
import com.slandrade.begins.vagas.domain.models.dto.CandidatoDTO;
import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import com.slandrade.begins.vagas.domain.repositories.CandidatoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidatoServiceImplTest {

    private static final UUID ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
    private static final String EMAIL = "email@dominio.com";
    private static final String CPF = "12345678900";

    @InjectMocks
    private CandidatoServiceImpl candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Test
    void testGetCandidatos() throws Exception {

        var candidatosModel = Collections.singletonList(criarCandidatoModelPadrao());

        var pageable = PageRequest.of(0, 20);
        var page = new PageImpl<>(candidatosModel);

        when(candidatoRepository.findAll(pageable)).thenReturn(page);

        var result = candidatoService.getCandidatos(pageable);
        assertAll(
                () -> assertEquals(criarCandidatoDTOPadrao(), result.get(0)),
                () -> assertEquals(1, result.size())
        );
    }

    @Test
    void testGetCandidatosNaoEncontrado() {

        var pageable = PageRequest.of(0, 20);

        when(candidatoRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(CandidatoNaoEncontradoException.class,
                () -> candidatoService.getCandidatos(pageable)
        );
    }

    @Test
    void testPostCandidato() throws Exception {

        var candidatoModel = criarCandidatoModelPadrao();
        var candidatoDTO = criarCandidatoDTOPadrao();

        when(candidatoRepository.findByEmailOrCpf(EMAIL, CPF)).thenReturn(Optional.empty());
        when(candidatoRepository.save(candidatoModel)).thenReturn(candidatoModel);

        var result = candidatoService.postCandidato(candidatoModel);
        assertEquals(candidatoDTO, result);
    }

    @Test
    void testPostCandidatoJaCadastrado() {

        var candidatoModel = criarCandidatoModelPadrao();
        var candidatoExistente = new CandidatoModel();

        when(candidatoRepository.findByEmailOrCpf("email@dominio.com", "12345678900")).thenReturn(Optional.of(candidatoExistente));

        assertThrows(EmailOuCpfJaCadastradoException.class,
                () -> candidatoService.postCandidato(candidatoModel)
        );
    }

    @Test
    void testUpdateCandidato() throws Exception {

        var candidatoModel = criarCandidatoModelPadrao();
        var candidatoDTO = criarCandidatoDTOPadrao();

        var candidatoExistente = new CandidatoModel();

        when(candidatoRepository.findById(ID)).thenReturn(Optional.of(candidatoExistente));
        when(candidatoRepository.save(candidatoModel)).thenAnswer(invocation -> invocation.getArgument(0));

        var result = candidatoService.updateCandidato(candidatoModel, ID);

        assertAll(
                () -> assertEquals(candidatoDTO, result),
                () -> assertThat(candidatoModel)
                        .usingRecursiveComparison()
                        .isEqualTo(candidatoExistente)
        );
    }

    @Test
    void testUpdateCandidatoNotFound() {

        var candidatoModel = criarCandidatoModelPadrao();

        when(candidatoRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(CandidatoNaoEncontradoException.class,
                () -> candidatoService.updateCandidato(candidatoModel, ID)
        );
    }

    @Test
    void testDeleteCandidato() throws Exception {

        var candidatoModel = criarCandidatoModelPadrao();

        when(candidatoRepository.findById(ID)).thenReturn(Optional.of(candidatoModel));

        candidatoService.deleteCandidato(ID);

        verify(candidatoRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteCandidatoNaoEncontrado() {

        when(candidatoRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(CandidatoNaoEncontradoException.class,
                () -> candidatoService.deleteCandidato(ID)
        );
    }

    @Test
    void testDeleteTodosCandidatos() {

        candidatoService.deleteCandidatos();

        verify(candidatoRepository, times(1)).deleteAll();
    }

    private CandidatoModel criarCandidatoModelPadrao() {
        return CandidatoModel.builder()
                .cpf(CPF)
                .email(EMAIL)
                .rg("123456789")
                .telefone("123456789")
                .nome("Teste")
                .experiencia("Teste")
                .build();
    }

    private CandidatoDTO criarCandidatoDTOPadrao() {
        return CandidatoDTO.convertToDTO(criarCandidatoModelPadrao());
    }
}