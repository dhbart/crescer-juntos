package dhbart.crescerjuntos.execucaotarefa.application;

import dhbart.crescerjuntos.execucaotarefa.application.dto.ExecucaoTarefaRequest;
import dhbart.crescerjuntos.execucaotarefa.application.dto.ExecucaoTarefaResponse;
import dhbart.crescerjuntos.execucaotarefa.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.execucaotarefa.domain.repository.ExecucaoTarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarExecucaoTarefaUseCase {
    private final ExecucaoTarefaRepository execucaoRepository;

    public ExecucaoTarefaResponse execute(ExecucaoTarefaRequest request) {
        if (execucaoRepository.existeExecucao(request.getTarefaId(), request.getCriancaId(), request.getDataExecucao())) {
            throw new IllegalStateException("Já existe execução para esta tarefa, criança e data");
        }
        var execucao = new ExecucaoTarefa(request.getTarefaId(), request.getCriancaId(), request.getDataExecucao());
        execucao.registrar(request.getObservacao());
        var salva = execucaoRepository.salvar(execucao);
        return ExecucaoTarefaResponse.from(salva);
    }
}