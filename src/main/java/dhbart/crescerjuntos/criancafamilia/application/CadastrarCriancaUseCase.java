package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaRequest;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaResponse;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CadastrarCriancaUseCase {

    private final CriancaRepository criancaRepository;
    private final FamiliaRepository familiaRepository;

    public CriancaResponse execute(CriancaRequest request) {
        if (!familiaRepository.existe(request.getFamiliaId())) {
            throw ResourceNotFoundException.familia(request.getFamiliaId());
        }
        Crianca crianca = new Crianca(request.getApelido(), request.getDataNascimento(), request.getFamiliaId());
        Crianca salva = criancaRepository.salvar(crianca);
        return CriancaResponse.from(salva);
    }
}