package dhbart.crescerjuntos.tarefa.domain.repository;

import dhbart.crescerjuntos.tarefa.domain.model.Tarefa;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TarefaRepository {
    Tarefa salvar(Tarefa tarefa);
    Optional<Tarefa> buscarPorId(Long id);
    List<Tarefa> buscarPorFamilia(Long familiaId);
    List<Tarefa> buscarAtivasPorFamilia(Long familiaId);
    List<Tarefa> buscarAtivasNaData(Long familiaId, LocalDate data);
    boolean existe(Long id);
    void excluir(Long id);
}