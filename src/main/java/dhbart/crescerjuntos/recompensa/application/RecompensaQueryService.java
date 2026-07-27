package dhbart.crescerjuntos.recompensa.application;

import dhbart.crescerjuntos.recompensa.application.dto.RecompensaResponse;
import dhbart.crescerjuntos.recompensa.domain.repository.RecompensaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecompensaQueryService {
    private final RecompensaRepository recompensaRepository;

    public RecompensaResponse buscarPorId(Long id) {
        var r = recompensaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.recompensa(id));
        return RecompensaResponse.from(r);
    }

    public List<RecompensaResponse> listarPorFamilia(Long familiaId) {
        return recompensaRepository.buscarPorFamilia(familiaId).stream()
                .map(RecompensaResponse::from).toList();
    }

    public List<RecompensaResponse> listarDisponiveis(Long familiaId) {
        return recompensaRepository.buscarDisponiveisPorFamilia(familiaId).stream()
                .map(RecompensaResponse::from).toList();
    }
}