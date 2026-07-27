package dhbart.crescerjuntos.resgate.infrastructure.persistence;

import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import dhbart.crescerjuntos.resgate.infrastructure.persistence.entity.ResgateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaResgateRepository extends JpaRepository<ResgateEntity, Long> {
    List<ResgateEntity> findByCriancaId(Long criancaId);
    List<ResgateEntity> findByCriancaIdAndStatus(Long criancaId, StatusResgate status);
}