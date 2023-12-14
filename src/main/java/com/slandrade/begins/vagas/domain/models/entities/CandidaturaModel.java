package com.slandrade.begins.vagas.domain.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CandidaturaModel {

    @Id
    @GeneratedValue
    @Column(name = "id_candidatura")
    private UUID idCandidatura;

    @JoinColumn(name = "id_candidato")
    @ManyToOne
    private CandidatoModel candidato;

    @JoinColumn(name = "id_vaga")
    @ManyToOne
    @JsonBackReference
    private VagaModel vaga;
}