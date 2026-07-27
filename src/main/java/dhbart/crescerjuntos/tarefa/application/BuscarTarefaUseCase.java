package dhbart.crescerjuntos.tarefa.application;

import dhbart.crescerjuntos.tarefa.application.dto.TarefaResponse;
import dhbart.crescerjuntos.tarefa.domain.repository.TarefaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarTarefaUseCase {
    private final TarefaRepository tarefaRepository;

    public TarefaResponse execute(Long id) {
        var tarefa = tarefaRepository.buscarPorId(id)
                .orElseThrow(() -> ResourceNotFoundException.tarefa(id));
        return TarefaResponse.from(tarefa);
    }
}