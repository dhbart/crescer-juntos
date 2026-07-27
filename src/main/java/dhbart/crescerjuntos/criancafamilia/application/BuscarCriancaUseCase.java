package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaResponse;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarCriancaUseCase {

    private final CriancaRepository criancaRepository;

    public CriancaResponse execute(Long id) {
        Crianca crianca = criancaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.crianca(id));
        return CriancaResponse.from(crianca);
    }
}