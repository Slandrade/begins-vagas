package com.slandrade.begins.vagas.application.controller;

import com.slandrade.begins.vagas.domain.exceptions.VagaNaoEncontradaException;
import com.slandrade.begins.vagas.domain.models.dto.VagaDTO;
import com.slandrade.begins.vagas.domain.models.entities.VagaModel;
import com.slandrade.begins.vagas.domain.services.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    @GetMapping("/")
    public ResponseEntity<List<VagaDTO>> getVagas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) throws VagaNaoEncontradaException {

        Pageable pageable = PageRequest.of(page, size);
        List<VagaDTO> vagas = vagaService.getVagas(pageable);

        return ResponseEntity.ok(vagas);
    }

    @PostMapping("/criar/")
    public ResponseEntity<VagaDTO> postVaga(@RequestBody VagaModel vagaModel) {

        var vagaDTO = vagaService.postVaga(vagaModel);

        return ResponseEntity.ok(vagaDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<VagaDTO> updateVaga(@RequestBody VagaModel vagaModel, @PathVariable UUID id) throws VagaNaoEncontradaException {

        var vagaDTO = vagaService.updateVaga(vagaModel, id);

        return ResponseEntity.ok(vagaDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deleteVaga(@PathVariable UUID id) throws VagaNaoEncontradaException {

        vagaService.deleteVaga(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar-todas/")
    public ResponseEntity<Object> deleteVagas() {

        vagaService.deleteVagas();

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/inativar/{id}")
    public ResponseEntity<VagaDTO> inativarVaga(@PathVariable UUID id) throws VagaNaoEncontradaException {

        vagaService.inativarVaga(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reativar/{id}")
    public ResponseEntity<VagaDTO> reativarVaga(@PathVariable UUID id) throws VagaNaoEncontradaException {

        vagaService.reativarVaga(id);

        return ResponseEntity.ok().build();
    }
}