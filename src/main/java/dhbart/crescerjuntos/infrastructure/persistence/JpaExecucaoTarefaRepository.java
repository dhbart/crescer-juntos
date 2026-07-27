package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.StatusExecucao;
import dhbart.crescerjuntos.infrastructure.persistence.entity.ExecucaoTarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaExecucaoTarefaRepository extends JpaRepository<ExecucaoTarefaEntity, Long> {
    
    List<ExecucaoTarefaEntity> findByCriancaId(Long criancaId);
    
    List<ExecucaoTarefaEntity> findByTarefaId(Long tarefaId);
    
    List<ExecucaoTarefaEntity> findByCriancaIdAndDataExecucao(Long criancaId, LocalDate dataExecucao);
    
    List<ExecucaoTarefaEntity> findByStatus(StatusExecucao status);
    
    Optional<ExecucaoTarefaEntity> findByTarefaIdAndCriancaIdAndDataExecucao(
        Long tarefaId, Long criancaId, LocalDate dataExecucao
    );
    
    @Query("SELECT e FROM ExecucaoTarefaEntity e WHERE e.crianca.id = :criancaId " +
           "AND e.dataExecucao BETWEEN :dataInicio AND :dataFim")
    List<ExecucaoTarefaEntity> findByCriancaIdAndPeriodo(
        @Param("criancaId") Long criancaId,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
    
    @Query("SELECT SUM(e.pontosAplicados) FROM ExecucaoTarefaEntity e " +
           "WHERE e.crianca.id = :criancaId AND e.status = 'APROVADA'")
    Integer somarPontosPorCrianca(@Param("criancaId") Long criancaId);
}