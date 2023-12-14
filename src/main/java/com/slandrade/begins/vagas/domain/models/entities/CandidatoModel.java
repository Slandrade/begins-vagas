package com.slandrade.begins.vagas.domain.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CandidatoModel {

    @Id
    @GeneratedValue
    @Column(name = "id_candidato")
    private UUID idCandidato;

    @NonNull
    @Column(name = "nome")
    private String nome;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "telefone")
    private String telefone;

    @NonNull
    @Column(name = "rg")
    private String rg;

    @NonNull
    @Column(name = "cpf")
    private String cpf;

    @NonNull
    @Column(name = "experiencia")
    private String experiencia;
}