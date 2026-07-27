package dhbart.crescerjuntos.execucaotarefa.application;

import dhbart.crescerjuntos.execucaotarefa.application.dto.ExecucaoTarefaResponse;
import dhbart.crescerjuntos.execucaotarefa.domain.repository.ExecucaoTarefaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarExecucaoTarefaUseCase {
    private final ExecucaoTarefaRepository execucaoRepository;

    public ExecucaoTarefaResponse execute(Long id) {
        var execucao = execucaoRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        return ExecucaoTarefaResponse.from(execucao);
    }

    public List<ExecucaoTarefaResponse> executePorCrianca(Long criancaId) {
        return execucaoRepository.buscarPorCrianca(criancaId).stream()
                .map(ExecucaoTarefaResponse::from).toList();
    }
}