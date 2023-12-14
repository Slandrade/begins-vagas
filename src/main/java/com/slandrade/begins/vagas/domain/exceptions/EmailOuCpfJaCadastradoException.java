package com.slandrade.begins.vagas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email ou cpf já está cadastrado")
public class EmailOuCpfJaCadastradoException extends Exception {

        public EmailOuCpfJaCadastradoException(String message) {
            super(message);
        }
}