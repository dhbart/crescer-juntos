package dhbart.crescerjuntos.criancafamilia.infrastructure.persistence;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.CriancaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaCriancaRepository extends JpaRepository<CriancaEntity, Long> {
    List<CriancaEntity> findByFamiliaId(Long familiaId);
    List<CriancaEntity> findByFamiliaIdAndAtivaTrue(Long familiaId);
    boolean existsByApelidoAndDataNascimentoAndFamiliaId(String apelido, LocalDate dataNascimento, Long familiaId);
}