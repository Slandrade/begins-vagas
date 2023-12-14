package com.slandrade.begins.vagas.domain.models.dto;

import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidaturaDTO {

    private VagaModel vaga;
    private CandidatoModel candidato;
}