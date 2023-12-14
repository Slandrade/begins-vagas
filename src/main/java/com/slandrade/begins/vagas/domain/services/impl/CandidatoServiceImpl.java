package com.slandrade.begins.vagas.domain.services.impl;

import com.slandrade.begins.vagas.domain.exceptions.CandidatoNaoEncontradoException;
import com.slandrade.begins.vagas.domain.exceptions.EmailOuCpfJaCadastradoException;
import com.slandrade.begins.vagas.domain.models.dto.CandidatoDTO;
import com.slandrade.begins.vagas.domain.models.entities.CandidatoModel;
import com.slandrade.begins.vagas.domain.repositories.CandidatoRepository;
import com.slandrade.begins.vagas.domain.services.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

    private static final String CANDIDATO_NAO_ENCONTRADO = "Candidato não encontrado";

    private final CandidatoRepository candidatoRepository;
    @Override
    public CandidatoDTO postCandidato(CandidatoModel candidatoModel) throws EmailOuCpfJaCadastradoException {

        var candidatoCriado = candidatoRepository.findByEmailOrCpf(candidatoModel.getEmail(), candidatoModel.getCpf());
        if (candidatoCriado.isPresent()) {
            throw new EmailOuCpfJaCadastradoException("Email ou cpf já cadastrado");
        }

        return CandidatoDTO.convertToDTO(candidatoRepository.save(candidatoModel));
    }

    @Override
    public List<CandidatoDTO> getCandidatos(Pageable pageable) throws CandidatoNaoEncontradoException {

        Page<CandidatoModel> candidatos = candidatoRepository.findAll(pageable);
        if (candidatos.isEmpty()) {
            throw new CandidatoNaoEncontradoException("Nenhum candidato encontrado");
        }

        return candidatos
                .map(CandidatoDTO::convertToDTO)
                .toList();
    }

    @Override
    public CandidatoDTO updateCandidato(CandidatoModel candidatoModel, UUID id) throws CandidatoNaoEncontradoException {
        return candidatoRepository.findById(id)
                .map(candidato -> {
                    candidato.setNome(candidatoModel.getNome());
                    candidato.setEmail(candidatoModel.getEmail());
                    candidato.setTelefone(candidatoModel.getTelefone());
                    candidato.setCpf(candidatoModel.getCpf());
                    candidato.setRg(candidatoModel.getRg());
                    candidato.setExperiencia(candidatoModel.getExperiencia());

                    var updatedCandidato = candidatoRepository.save(candidato);

                    return CandidatoDTO.convertToDTO(updatedCandidato);
                })
                .orElseThrow(() -> new CandidatoNaoEncontradoException(CANDIDATO_NAO_ENCONTRADO));
    }

    @Override
    public void deleteCandidato(UUID id) throws CandidatoNaoEncontradoException {

        Optional<CandidatoModel> candidato = candidatoRepository.findById(id);
        if (candidato.isEmpty()) {
            throw new CandidatoNaoEncontradoException(CANDIDATO_NAO_ENCONTRADO);
        }
        candidatoRepository.deleteById(id);
    }

    @Override
    public void deleteCandidatos() {

        candidatoRepository.deleteAll();
    }
}