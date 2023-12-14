package com.slandrade.begins.vagas.application.controller;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.EmailOuCpfJaCadastradoException;
import com.slandrade.begins.vagas.domain.models.dto.CandidatoDTO;
import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import com.slandrade.begins.vagas.domain.services.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candidatos")
public class CandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping("/")
    public ResponseEntity<List<CandidatoDTO>> getCandidatos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) throws CandidatoNaoEncontradoException {

        Pageable pageable = PageRequest.of(page, size);
        List<CandidatoDTO> candidatos = candidatoService.getCandidatos(pageable);
        return ResponseEntity.ok(candidatos);
    }

    @PostMapping("/criar/")
    public ResponseEntity<CandidatoDTO> postCandidato(@RequestBody CandidatoModel candidatoModel) throws EmailOuCpfJaCadastradoException {

        var candidatoDTO = candidatoService.postCandidato(candidatoModel);

        return ResponseEntity.ok(candidatoDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CandidatoDTO> updateCandidato(@RequestBody CandidatoModel candidatoModel, @PathVariable UUID id) throws CandidatoNaoEncontradoException {

        var candidatoUpdated = candidatoService.updateCandidato(candidatoModel, id);

        return ResponseEntity.ok(candidatoUpdated);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deleteCandidato(@PathVariable UUID id) throws CandidatoNaoEncontradoException {

        candidatoService.deleteCandidato(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar-todos/")
    public ResponseEntity<Object> deleteCandidatos() {

        candidatoService.deleteCandidatos();

        return ResponseEntity.ok().build();
    }
}