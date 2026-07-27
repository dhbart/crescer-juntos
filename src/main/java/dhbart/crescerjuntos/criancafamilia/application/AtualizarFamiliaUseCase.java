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
@Transactional
public class AtualizarFamiliaUseCase {

    private final FamiliaRepository familiaRepository;

    public FamiliaResponse execute(Long id, String novoNome) {
        Familia familia = familiaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));
        familia.atualizarNome(novoNome);
        Familia salva = familiaRepository.salvar(familia);
        return FamiliaResponse.from(salva);
    }
}