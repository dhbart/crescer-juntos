package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.Recompensa;
import dhbart.crescerjuntos.domain.repository.RecompensaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.RecompensaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecompensaDomainRepository implements RecompensaRepository {

    private final JpaRecompensaRepository jpaRepo;
    private final RecompensaEntityMapper mapper;
    private final JpaFamiliaRepository jpaFamiliaRepo;

    @Override
    public Recompensa salvar(Recompensa recompensa) {
        var familiaEntity = jpaFamiliaRepo.findById(recompensa.getFamiliaId()).orElse(null);
        var entity = mapper.toEntity(recompensa, familiaEntity);
        var saved = jpaRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Recompensa> buscarPorId(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Recompensa> buscarPorFamilia(Long familiaId) {
        return jpaRepo.findByFamiliaId(familiaId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Recompensa> buscarDisponiveisPorFamilia(Long familiaId) {
        return jpaRepo.findByFamiliaIdAndDisponivelTrue(familiaId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existe(Long id) {
        return jpaRepo.existsById(id);
    }

    @Override
    public void excluir(Long id) {
        jpaRepo.deleteById(id);
    }
}
