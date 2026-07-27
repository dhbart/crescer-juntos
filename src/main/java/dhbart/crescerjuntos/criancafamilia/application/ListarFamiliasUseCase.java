package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Familia;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.criancafamilia.application.dto.FamiliaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarFamiliasUseCase {

    private final FamiliaRepository familiaRepository;

    public List<FamiliaResponse> execute(boolean apenasAtivas) {
        List<Familia> familias = apenasAtivas ? familiaRepository.buscarAtivas() : familiaRepository.buscarTodas();
        return familias.stream().map(FamiliaResponse::from).toList();
    }
}