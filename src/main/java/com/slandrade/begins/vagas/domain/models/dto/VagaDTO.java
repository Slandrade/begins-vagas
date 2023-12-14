package com.slandrade.begins.vagas.domain.models.dto;

import com.slandrade.begins.vagas.domain.models.entities.CandidaturaModel;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.models.enums.TipoVagaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VagaDTO {

    private UUID idVaga;

    private Integer quantidadeVagas;
    private String titulo;
    private String descricao;
    private String requisitos;
    private String beneficios;
    private String nivel;
    private StatusEnum statusVaga;
    private TipoVagaEnum tipoVaga;
    private LocalDateTime dataPublicacao;
    private List<CandidaturaModel> candidaturas;

    public static VagaDTO convertToDTO(VagaModel vagaModel) {
        return VagaDTO.builder()
                .idVaga(vagaModel.getIdVaga())
                .titulo(vagaModel.getTitulo())
                .descricao(vagaModel.getDescricao())
                .requisitos(vagaModel.getRequisitos())
                .beneficios(vagaModel.getBeneficios())
                .nivel(vagaModel.getNivelExperiencia())
                .quantidadeVagas(vagaModel.getQuantidadeVagas())
                .dataPublicacao(vagaModel.getDataPublicacao())
                .statusVaga(vagaModel.getStatusVaga())
                .tipoVaga(vagaModel.getTipoVaga())
                .candidaturas(vagaModel.getCandidaturas())
                .build();
    }

}