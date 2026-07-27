package dhbart.crescerjuntos.domain.repository;  
  
import dhbart.crescerjuntos.domain.model.Tarefa;  
import java.time.LocalDate;  
import java.util.List;  
import java.util.Optional;  
  
public interface TarefaRepository {  
    Tarefa salvar(Tarefa tarefa);  
    Optional<Tarefa> buscarPorId(Long id);  
    List<Tarefa> buscarPorFamilia(Long familiaId);  
    List<Tarefa> buscarAtivasPorFamilia(Long familiaId);  
    List<Tarefa> buscarPorCrianca(Long criancaId);  
    List<Tarefa> buscarAtivasNaData(Long familiaId, LocalDate data);  
    boolean existe(Long id);  
    void excluir(Long id);  
}  
