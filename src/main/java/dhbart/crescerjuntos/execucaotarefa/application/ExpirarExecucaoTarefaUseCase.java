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
public class ExpirarExecucaoTarefaUseCase {
    private final ExecucaoTarefaRepository execucaoRepository;

    public ExecucaoTarefaResponse execute(Long id) {
        var execucao = execucaoRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        execucao.expirar();
        var salva = execucaoRepository.salvar(execucao);
        return ExecucaoTarefaResponse.from(salva);
    }
}