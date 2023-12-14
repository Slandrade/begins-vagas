package com.slandrade.begins.vagas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Candidato não encontrado")
public class CandidatoNaoEncontradoException extends Exception{

        public CandidatoNaoEncontradoException(String message) {
            super(message);
        }
}