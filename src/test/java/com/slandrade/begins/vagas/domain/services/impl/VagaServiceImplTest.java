package com.slandrade.begins.vagas.domain.services.impl;

import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.dto.VagaDTO;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.models.enums.TipoVagaEnum;
import com.slandrade.begins.vagas.domain.repositories.VagaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VagaServiceImplTest {

    private static final UUID ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
    private static final LocalDateTime DATA_PUBLICACAO = LocalDateTime.of(2021, 10, 1, 10, 0);

    @InjectMocks
    private VagaServiceImpl vagaService;

    @Mock
    private VagaRepository vagaRepository;

    @Test
    void testGetVagas() throws Exception {

        var vagasModel = Collections.singletonList(criarVagaModelPadrao());

        var pageable = PageRequest.of(0, 20);
        var page = new PageImpl<>(vagasModel);

        when(vagaRepository.findAll(pageable)).thenReturn(page);

        var result = vagaService.getVagas(pageable);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(criarVagaDTOPadrao(), result.get(0))
        );
    }

    @Test
    void testGetVagasNaoEncontradas() {

        var pageable = PageRequest.of(0, 20);
        when(vagaRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(VagaNaoEncontradaException.class,
                () -> vagaService.getVagas(pageable)
        );
    }

    @Test
    void testPostVaga() {

        var vagaModel = spy(criarVagaModelPadrao());
        var vagaDTO = criarVagaDTOPadrao();

        when(vagaRepository.save(vagaModel)).thenReturn(vagaModel);

        var result = vagaService.postVaga(vagaModel);

        assertAll(
                () -> verify(vagaModel).setStatusVaga(StatusEnum.ATIVO),
                () -> verify(vagaModel).setDataPublicacao(any(LocalDateTime.class)),
                () -> assertThat(result)
                        .usingRecursiveComparison()
                        .ignoringFields("dataPublicacao")
                        .isEqualTo(vagaDTO)
        );
    }

    @Test
    void testUpdateVaga() throws Exception {

        var vagaModel = criarVagaModelPadrao();
        var vagaDTO = criarVagaDTOPadrao();

        var vagaExistente = new VagaModel();

        when(vagaRepository.findById(ID)).thenReturn(Optional.of(vagaExistente));
        when(vagaRepository.save(vagaModel)).thenAnswer(invocation -> invocation.getArgument(0));

        var result = vagaService.updateVaga(vagaModel, ID);

        assertAll(
                () -> assertEquals(vagaDTO, result),
                () -> assertThat(vagaModel)
                        .usingRecursiveComparison()
                        .isEqualTo(vagaExistente)
        );
    }

    @Test
    void testUpdateVagaNaoEncontrada() {

        var vagaModel = criarVagaModelPadrao();

        when(vagaRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(VagaNaoEncontradaException.class,
                () -> vagaService.updateVaga(vagaModel, ID)
        );
    }

    @Test
    void testDeleteVaga() throws Exception {

        var vagaModel = criarVagaModelPadrao();

        when(vagaRepository.findById(ID)).thenReturn(Optional.of(vagaModel));

        vagaService.deleteVaga(ID);

        verify(vagaRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteVagaNaoEncontrada() {

        when(vagaRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(VagaNaoEncontradaException.class,
                () -> vagaService.deleteVaga(ID)
        );
    }

    @Test
    void testDeleteTodasVagas() {

        vagaService.deleteVagas();

        verify(vagaRepository, times(1)).deleteAll();
    }

    @Test
    void testInativarVaga() throws Exception {

        var vagaModel = criarVagaModelPadrao();

        when(vagaRepository.findById(ID)).thenReturn(Optional.of(vagaModel));

        vagaService.inativarVaga(ID);

        assertAll(
                () -> assertEquals(StatusEnum.INATIVO, vagaModel.getStatusVaga()),
                () -> verify(vagaRepository, times(1)).save(vagaModel)
        );
    }

    @Test
    void testInativarVagaNaoEncontrada() {

        when(vagaRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(VagaNaoEncontradaException.class,
                () -> vagaService.inativarVaga(ID)
        );
    }

    @Test
    void testReativarVaga() throws Exception {

        var vagaModel = criarVagaModelPadrao();
        vagaModel.setStatusVaga(StatusEnum.INATIVO);

        when(vagaRepository.findById(ID)).thenReturn(Optional.of(vagaModel));

        vagaService.reativarVaga(ID);

        assertAll(
                () -> assertEquals(StatusEnum.ATIVO, vagaModel.getStatusVaga()),
                () -> verify(vagaRepository, times(1)).save(vagaModel)
        );
    }

    @Test
    void testReativarVagaNaoEncontrada() {

        when(vagaRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(VagaNaoEncontradaException.class,
                () -> vagaService.reativarVaga(ID)
        );
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
                .dataPublicacao(DATA_PUBLICACAO)
                .statusVaga(StatusEnum.ATIVO)
                .build();
    }

    private VagaDTO criarVagaDTOPadrao() {
        return VagaDTO.convertToDTO(criarVagaModelPadrao());
    }
}