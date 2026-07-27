package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.Familia;
import dhbart.crescerjuntos.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.FamiliaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FamiliaDomainRepository implements FamiliaRepository {

    private final JpaFamiliaRepository jpaFamiliaRepository;
    private final FamiliaEntityMapper familiaEntityMapper;

    @Override
    public Familia salvar(Familia familia) {
        var entity = familiaEntityMapper.toEntity(familia);
        var saved = jpaFamiliaRepository.save(entity);
        return familiaEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Familia> buscarPorId(Long id) {
        return jpaFamiliaRepository.findById(id).map(familiaEntityMapper::toDomain);
    }

    @Override
    public List<Familia> buscarTodas() {
        return jpaFamiliaRepository.findAll().stream()
                .map(familiaEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Familia> buscarAtivas() {
        return jpaFamiliaRepository.findByAtivaTrue().stream()
                .map(familiaEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existe(Long id) {
        return jpaFamiliaRepository.existsById(id);
    }

    @Override
    public void excluir(Long id) {
        jpaFamiliaRepository.deleteById(id);
    }
}
