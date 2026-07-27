package dhbart.crescerjuntos.domain.repository;  
  
import dhbart.crescerjuntos.domain.model.ExecucaoTarefa;  
import dhbart.crescerjuntos.domain.model.StatusExecucao;  
import java.time.LocalDate;  
import java.util.List;  
import java.util.Optional;  
  
public interface ExecucaoTarefaRepository {  
    ExecucaoTarefa salvar(ExecucaoTarefa execucao);  
    Optional<ExecucaoTarefa> buscarPorId(Long id);  
    List<ExecucaoTarefa> buscarPorCrianca(Long criancaId);  
    List<ExecucaoTarefa> buscarPorCriancaEStatus(Long criancaId, StatusExecucao status);  
    Optional<ExecucaoTarefa> buscarPorTarefaCriancaEData(Long tarefaId, Long criancaId, LocalDate data);  
    boolean existeExecucao(Long tarefaId, Long criancaId, LocalDate data);  
    void excluir(Long id);  
}  
