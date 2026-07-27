package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.infrastructure.persistence.entity.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaTarefaRepository extends JpaRepository<TarefaEntity, Long> {
    
    List<TarefaEntity> findByFamiliaId(Long familiaId);
    
    List<TarefaEntity> findByFamiliaIdAndAtivaTrue(Long familiaId);
    
    @Query("SELECT t FROM TarefaEntity t WHERE t.familia.id = :familiaId AND t.ativa = true " +
           "AND (t.dataInicio IS NULL OR t.dataInicio <= :data) " +
           "AND (t.dataFim IS NULL OR t.dataFim >= :data)")
    List<TarefaEntity> findAtivasNaData(@Param("familiaId") Long familiaId, @Param("data") LocalDate data);
}