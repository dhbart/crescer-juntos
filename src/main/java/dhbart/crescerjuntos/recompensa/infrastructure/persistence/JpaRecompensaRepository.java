package dhbart.crescerjuntos.recompensa.infrastructure.persistence;

import dhbart.crescerjuntos.recompensa.infrastructure.persistence.entity.RecompensaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaRecompensaRepository extends JpaRepository<RecompensaEntity, Long> {
    List<RecompensaEntity> findByFamiliaId(Long familiaId);
    List<RecompensaEntity> findByFamiliaIdAndDisponivelTrue(Long familiaId);
}