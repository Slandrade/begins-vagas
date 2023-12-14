package com.slandrade.begins.vagas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Vaga não encontrada")
public class VagaNaoEncontradaException extends Exception {

        public VagaNaoEncontradaException(String message) {
            super(message);
        }
}