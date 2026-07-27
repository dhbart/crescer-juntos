package dhbart.crescerjuntos.criancafamilia.infrastructure.persistence;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.FamiliaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFamiliaRepository extends JpaRepository<FamiliaEntity, Long> {
    List<FamiliaEntity> findByAtivaTrue();
}