package com.slandrade.begins.vagas.domain.models.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }
}