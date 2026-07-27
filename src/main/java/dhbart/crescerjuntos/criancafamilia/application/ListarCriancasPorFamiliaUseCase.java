package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarCriancasPorFamiliaUseCase {

    private final CriancaRepository criancaRepository;

    public List<CriancaResponse> execute(Long familiaId, boolean apenasAtivas) {
        List<Crianca> criancas = apenasAtivas
                ? criancaRepository.buscarAtivasPorFamilia(familiaId)
                : criancaRepository.buscarPorFamilia(familiaId);
        return criancas.stream().map(CriancaResponse::from).toList();
    }
}