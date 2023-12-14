package com.slandrade.begins.vagas.domain.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.slandrade.begins.vagas.domain.models.enums.StatusEnum;
import com.slandrade.begins.vagas.domain.models.enums.TipoVagaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class VagaModel {

    @Id
    @GeneratedValue
    @Column(name = "id_vaga")
    private UUID idVaga;

    @NonNull
    @Column(name = "titulo")
    private String titulo;

    @NonNull
    @Column(name = "descricao")
    private String descricao;

    @NonNull
    @Column(name = "requisitos")
    private String requisitos;

    @NonNull
    @Column(name = "beneficios")
    private String beneficios;

    @NonNull
    @Column(name = "nivel")
    private String nivelExperiencia;

    @NonNull
    @Column(name = "qtd_vagas")
    private Integer quantidadeVagas;

    @NonNull
    @Column(name = "data_publicacao")
    private LocalDateTime dataPublicacao;

    @NonNull
    @Column(name = "tipo_vaga")
    @Enumerated(EnumType.STRING)
    private TipoVagaEnum tipoVaga;

    @NonNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum statusVaga;

    @OneToMany(mappedBy = "vaga")
    @Column(name = "candidaturas")
    @JsonManagedReference
    private List<CandidaturaModel> candidaturas;
}