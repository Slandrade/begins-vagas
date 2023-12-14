package com.slandrade.begins.vagas.domain.models.enums;

import lombok.Getter;

@Getter
public enum TipoVagaEnum {
    CLT("CLT"),
    PJ("PJ"),
    FREELANCER("Freelancer");

    private final String tipoVaga;

    TipoVagaEnum(String tipoVaga) {
        this.tipoVaga = tipoVaga;
    }
}