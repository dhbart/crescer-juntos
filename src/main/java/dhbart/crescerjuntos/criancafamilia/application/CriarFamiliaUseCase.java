package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Familia;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.criancafamilia.application.dto.FamiliaRequest;
import dhbart.crescerjuntos.criancafamilia.application.dto.FamiliaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriarFamiliaUseCase {

    private final FamiliaRepository familiaRepository;

    public FamiliaResponse execute(FamiliaRequest request) {
        Familia familia = new Familia(request.getNome());
        Familia salva = familiaRepository.salvar(familia);
        return FamiliaResponse.from(salva);
    }
}