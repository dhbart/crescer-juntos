package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Familia;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DesativarFamiliaUseCase {

    private final FamiliaRepository familiaRepository;

    public void execute(Long id) {
        Familia familia = familiaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));
        familia.desativar();
        familiaRepository.salvar(familia);
    }
}