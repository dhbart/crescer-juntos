package dhbart.crescerjuntos.resgate.application;

import dhbart.crescerjuntos.resgate.application.dto.ResgateResponse;
import dhbart.crescerjuntos.resgate.domain.repository.ResgateRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MarcarEntregaResgateUseCase {
    private final ResgateRepository resgateRepository;

    public ResgateResponse execute(Long id) {
        var resgate = resgateRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.resgate(id));
        resgate.entregar();
        var salvo = resgateRepository.salvar(resgate);
        return ResgateResponse.from(salvo);
    }
}