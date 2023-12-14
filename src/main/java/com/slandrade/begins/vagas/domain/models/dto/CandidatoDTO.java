package com.slandrade.begins.vagas.domain.models.dto;

import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatoDTO {
        private UUID idCandidato;

        private String nome;
        private String email;
        private String telefone;
        private String rg;
        private String cpf;
        private String experiencia;

        public static CandidatoDTO convertToDTO(CandidatoModel candidatoModel) {
            return CandidatoDTO.builder()
                    .idCandidato(candidatoModel.getIdCandidato())
                    .nome(candidatoModel.getNome())
                    .email(candidatoModel.getEmail())
                    .telefone(candidatoModel.getTelefone())
                    .rg(candidatoModel.getRg())
                    .cpf(candidatoModel.getCpf())
                    .experiencia(candidatoModel.getExperiencia())
                    .build();
        }
}