package dhbart.crescerjuntos.recompensa.application;

import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.recompensa.application.dto.RecompensaRequest;
import dhbart.crescerjuntos.recompensa.application.dto.RecompensaResponse;
import dhbart.crescerjuntos.recompensa.domain.model.Recompensa;
import dhbart.crescerjuntos.recompensa.domain.repository.RecompensaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriarRecompensaUseCase {
    private final RecompensaRepository recompensaRepository;
    private final FamiliaRepository familiaRepository;

    public RecompensaResponse execute(RecompensaRequest request) {
        if (!familiaRepository.existe(request.getFamiliaId()))
            throw ResourceNotFoundException.familia(request.getFamiliaId());
        var recompensa = new Recompensa(request.getNome(), request.getCustoPontos(), request.getFamiliaId());
        recompensa.setDescricao(request.getDescricao());
        var salva = recompensaRepository.salvar(recompensa);
        return RecompensaResponse.from(salva);
    }
}