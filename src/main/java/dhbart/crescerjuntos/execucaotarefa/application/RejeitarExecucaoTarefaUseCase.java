package dhbart.crescerjuntos.execucaotarefa.application;

import dhbart.crescerjuntos.execucaotarefa.application.dto.ExecucaoTarefaResponse;
import dhbart.crescerjuntos.execucaotarefa.domain.repository.ExecucaoTarefaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RejeitarExecucaoTarefaUseCase {
    private final ExecucaoTarefaRepository execucaoRepository;

    public ExecucaoTarefaResponse execute(Long id, String motivo) {
        var execucao = execucaoRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        execucao.rejeitar(motivo);
        var salva = execucaoRepository.salvar(execucao);
        return ExecucaoTarefaResponse.from(salva);
    }
}