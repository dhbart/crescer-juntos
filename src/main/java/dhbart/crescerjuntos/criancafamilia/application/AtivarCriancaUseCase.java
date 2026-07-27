package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AtivarCriancaUseCase {

    private final CriancaRepository criancaRepository;

    public void execute(Long id) {
        Crianca crianca = criancaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.crianca(id));
        crianca.ativar();
        criancaRepository.salvar(crianca);
    }
}