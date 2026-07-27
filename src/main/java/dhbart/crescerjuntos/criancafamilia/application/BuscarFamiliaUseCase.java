package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Familia;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.criancafamilia.application.dto.FamiliaResponse;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarFamiliaUseCase {

    private final FamiliaRepository familiaRepository;

    public FamiliaResponse execute(Long id) {
        Familia familia = familiaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));
        return FamiliaResponse.from(familia);
    }
}