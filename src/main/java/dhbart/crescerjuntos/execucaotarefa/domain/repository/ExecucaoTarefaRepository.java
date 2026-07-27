package dhbart.crescerjuntos.execucaotarefa.domain.repository;

import dhbart.crescerjuntos.execucaotarefa.domain.model.ExecucaoTarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExecucaoTarefaRepository {
    ExecucaoTarefa salvar(ExecucaoTarefa execucao);
    Optional<ExecucaoTarefa> buscarPorId(Long id);
    List<ExecucaoTarefa> buscarPorCrianca(Long criancaId);
    boolean existeExecucao(Long tarefaId, Long criancaId, LocalDate data);
    void excluir(Long id);
}