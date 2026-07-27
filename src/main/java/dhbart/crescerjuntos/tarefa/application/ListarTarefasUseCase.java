package dhbart.crescerjuntos.tarefa.application;

import dhbart.crescerjuntos.tarefa.application.dto.TarefaResponse;
import dhbart.crescerjuntos.tarefa.domain.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListarTarefasUseCase {
    private final TarefaRepository tarefaRepository;

    public List<TarefaResponse> executePorFamilia(Long familiaId, boolean apenasAtivas) {
        var tarefas = apenasAtivas
                ? tarefaRepository.buscarAtivasPorFamilia(familiaId)
                : tarefaRepository.buscarPorFamilia(familiaId);
        return tarefas.stream().map(TarefaResponse::from).toList();
    }

    public List<TarefaResponse> executeAtivasNaData(Long familiaId, LocalDate data) {
        return tarefaRepository.buscarAtivasNaData(familiaId, data).stream()
                .map(TarefaResponse::from).toList();
    }
}