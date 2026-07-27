package dhbart.crescerjuntos.recompensa.infrastructure.persistence;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.JpaFamiliaRepository;
import dhbart.crescerjuntos.recompensa.domain.model.Recompensa;
import dhbart.crescerjuntos.recompensa.domain.repository.RecompensaRepository;
import dhbart.crescerjuntos.recompensa.infrastructure.persistence.mapper.RecompensaEntityMapper;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecompensaRepositoryImpl implements RecompensaRepository {

    private final JpaRecompensaRepository jpaRecompensaRepository;
    private final RecompensaEntityMapper mapper;
    private final JpaFamiliaRepository jpaFamiliaRepository;

    @Override
    public Recompensa salvar(Recompensa recompensa) {
        var familia = jpaFamiliaRepository.findById(recompensa.getFamiliaId())
                .orElseThrow(() -> ResourceNotFoundException.familia(recompensa.getFamiliaId()));
        var entity = mapper.toEntity(recompensa, familia);
        var saved = jpaRecompensaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Recompensa> buscarPorId(Long id) {
        return jpaRecompensaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Recompensa> buscarPorFamilia(Long familiaId) {
        return jpaRecompensaRepository.findByFamiliaId(familiaId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Recompensa> buscarDisponiveisPorFamilia(Long familiaId) {
        return jpaRecompensaRepository.findByFamiliaIdAndDisponivelTrue(familiaId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existe(Long id) { return jpaRecompensaRepository.existsById(id); }

    @Override
    public void excluir(Long id) { jpaRecompensaRepository.deleteById(id); }
}