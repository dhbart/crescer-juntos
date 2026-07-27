package dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence;

import dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence.entity.ExecucaoTarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaExecucaoTarefaRepository extends JpaRepository<ExecucaoTarefaEntity, Long> {
    List<ExecucaoTarefaEntity> findByCriancaId(Long criancaId);

    boolean existsByTarefaIdAndCriancaIdAndDataExecucao(Long tarefaId, Long criancaId, LocalDate dataExecucao);

    @Query("SELECT COALESCE(SUM(e.pontosAplicados), 0) FROM ExecucaoTarefaEntity e WHERE e.crianca.id = :criancaId AND e.status = 'APROVADA'")
    Integer somarPontosPorCrianca(Long criancaId);
}