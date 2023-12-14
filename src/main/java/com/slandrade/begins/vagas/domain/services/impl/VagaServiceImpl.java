package com.slandrade.begins.vagas.domain.services.impl;

import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.dto.VagaDTO;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.repositories.VagaRepository;
import com.slandrade.begins.vagas.domain.services.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VagaServiceImpl implements VagaService {

    private static final String VAGA_NAO_ENCONTRADA = "Vaga não encontrada";

    private final VagaRepository vagaRepository;

    @Override
    public VagaDTO postVaga(VagaModel vagaModel) {

        vagaModel.setStatusVaga(StatusEnum.ATIVO);
        vagaModel.setDataPublicacao(LocalDateTime.now());
        return VagaDTO.convertToDTO(vagaRepository.save(vagaModel));
    }

    @Override
    public List<VagaDTO> getVagas(Pageable pageable) throws VagaNaoEncontradaException {

        Page<VagaModel> vagas = vagaRepository.findAll(pageable);
        if (vagas.isEmpty()) {
            throw new VagaNaoEncontradaException("Nenhuma vaga encontrada");
        }
        return vagas
                .map(VagaDTO::convertToDTO)
                .toList();
    }

    @Override
    public VagaDTO updateVaga(VagaModel vagaModel, UUID id) throws VagaNaoEncontradaException {
        return vagaRepository.findById(id)
                .map(vaga -> {
                    vaga.setTitulo(vagaModel.getTitulo());
                    vaga.setDescricao(vagaModel.getDescricao());
                    vaga.setBeneficios(vagaModel.getBeneficios());
                    vaga.setRequisitos(vagaModel.getRequisitos());
                    vaga.setQuantidadeVagas(vagaModel.getQuantidadeVagas());
                    vaga.setTipoVaga(vagaModel.getTipoVaga());
                    vaga.setNivelExperiencia(vagaModel.getNivelExperiencia());
                    vaga.setDataPublicacao(vagaModel.getDataPublicacao());
                    vaga.setStatusVaga(vagaModel.getStatusVaga());

                    var updatedVaga = vagaRepository.save(vaga);

                    return VagaDTO.convertToDTO(updatedVaga);
                })
                .orElseThrow(() -> new VagaNaoEncontradaException(VAGA_NAO_ENCONTRADA));
    }


    @Override
    public void deleteVaga(UUID id) throws VagaNaoEncontradaException {

        Optional<VagaModel> vaga = vagaRepository.findById(id);
        if (vaga.isEmpty()) {
            throw new VagaNaoEncontradaException(VAGA_NAO_ENCONTRADA);
        }

        vagaRepository.deleteById(id);
    }

    @Override
    public void deleteVagas() {

        vagaRepository.deleteAll();
    }

    @Override
    public void inativarVaga(UUID id) throws VagaNaoEncontradaException {

        Optional<VagaModel> vaga = vagaRepository.findById(id);
        if (vaga.isEmpty()) {
            throw new VagaNaoEncontradaException(VAGA_NAO_ENCONTRADA);
        }
        vaga.get().setStatusVaga(StatusEnum.INATIVO);
        vagaRepository.save(vaga.get());
    }

    @Override
    public void reativarVaga(UUID id) throws VagaNaoEncontradaException {

        Optional<VagaModel> vaga = vagaRepository.findById(id);
        if (vaga.isEmpty()) {
            throw new VagaNaoEncontradaException(VAGA_NAO_ENCONTRADA);
        }
        vaga.get().setStatusVaga(StatusEnum.ATIVO);
        vagaRepository.save(vaga.get());
    }
}