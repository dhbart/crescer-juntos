package dhbart.crescerjuntos.resgate.application;

import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import dhbart.crescerjuntos.resgate.application.dto.ResgateResponse;
import dhbart.crescerjuntos.resgate.domain.repository.ResgateRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResgateQueryService {
    private final ResgateRepository resgateRepository;

    public ResgateResponse buscarPorId(Long id) {
        return ResgateResponse.from(resgateRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.resgate(id)));
    }

    public List<ResgateResponse> listarPorCrianca(Long criancaId) {
        return resgateRepository.buscarPorCrianca(criancaId).stream()
                .map(ResgateResponse::from).toList();
    }

    public List<ResgateResponse> listarPorCriancaEStatus(Long criancaId, StatusResgate status) {
        return resgateRepository.buscarPorCriancaEStatus(criancaId, status).stream()
                .map(ResgateResponse::from).toList();
    }
}