package com.slandrade.begins.vagas.domain.exceptions.advice;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoJaInscritoException;
import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.EmailOuCpfJaCadastradoException;
import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.exceptions.custom.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final HttpStatus STATUS_NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus STATUS_CONFLICT = HttpStatus.CONFLICT;

    @ExceptionHandler(EmailOuCpfJaCadastradoException.class)
    public ResponseEntity<CustomErrorResponse> handleEmailOuCpfJaCadastradoException(EmailOuCpfJaCadastradoException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(STATUS_CONFLICT.value());
        errorResponse.setError("Conflict");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/candidatos/");

        return new ResponseEntity<>(errorResponse, STATUS_CONFLICT);
    }

    @ExceptionHandler(CandidatoNaoEncontradoException.class)
    public ResponseEntity<CustomErrorResponse> handleCandidatoNaoEncontradoException(CandidatoNaoEncontradoException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(STATUS_NOT_FOUND.value());
        errorResponse.setError("Not found");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/candidatos/{id}");

        return new ResponseEntity<>(errorResponse, STATUS_NOT_FOUND);
    }

    @ExceptionHandler(VagaNaoEncontradaException.class)
    public ResponseEntity<CustomErrorResponse> handleVagaNaoEncontradaException(VagaNaoEncontradaException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(STATUS_NOT_FOUND.value());
        errorResponse.setError("Not found");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/vagas/{id}");

        return new ResponseEntity<>(errorResponse, STATUS_NOT_FOUND);
    }

    @ExceptionHandler(CandidatoJaInscritoException.class)
    public ResponseEntity<CustomErrorResponse> handleCandidatoJaInscritoException(CandidatoJaInscritoException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(STATUS_CONFLICT.value());
        errorResponse.setError("Conflict");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath("/candidaturas/");

        return new ResponseEntity<>(errorResponse, STATUS_CONFLICT);
    }
}