package dhbart.crescerjuntos.tarefa.application;

import dhbart.crescerjuntos.tarefa.application.dto.TarefaRequest;
import dhbart.crescerjuntos.tarefa.application.dto.TarefaResponse;
import dhbart.crescerjuntos.tarefa.domain.model.Tarefa;
import dhbart.crescerjuntos.tarefa.domain.repository.TarefaRepository;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriarTarefaUseCase {

    private final TarefaRepository tarefaRepository;
    private final FamiliaRepository familiaRepository;

    public TarefaResponse execute(TarefaRequest request) {
        if (!familiaRepository.existe(request.getFamiliaId())) {
            throw ResourceNotFoundException.familia(request.getFamiliaId());
        }
        var tarefa = new Tarefa(request.getTitulo(), request.getFrequencia(), request.getFamiliaId(), request.getPontos());
        if (request.getDescricao() != null) tarefa.setDescricao(request.getDescricao());
        if (request.getHorarioPreferido() != null) tarefa.setHorarioPreferido(request.getHorarioPreferido());
        var salva = tarefaRepository.salvar(tarefa);
        return TarefaResponse.from(salva);
    }
}