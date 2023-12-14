package com.slandrade.begins.vagas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Candidato já inscrito nesta vaga")
public class CandidatoJaInscritoException extends Exception {

        public CandidatoJaInscritoException(String message) {
            super(message);
        }
}