package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.Crianca;
import dhbart.crescerjuntos.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.CriancaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CriancaDomainRepository implements CriancaRepository {

    private final JpaCriancaRepository jpaCriancaRepository;
    private final CriancaEntityMapper criancaMapper;

    @Override
    public Crianca salvar(Crianca crianca) {
        var entity = jpaCriancaRepository.findById(crianca.getId() != null ? crianca.getId() : 0L)
                .orElse(null);
        var familiaEntity = entity != null ? entity.getFamilia() : null;
        var saved = jpaCriancaRepository.save(criancaMapper.toEntity(crianca, familiaEntity));
        return criancaMapper.toDomain(saved);
    }

    @Override
    public Optional<Crianca> buscarPorId(Long id) {
        return jpaCriancaRepository.findById(id).map(criancaMapper::toDomain);
    }

    @Override
    public List<Crianca> buscarPorFamilia(Long familiaId) {
        return jpaCriancaRepository.findByFamiliaId(familiaId).stream()
                .map(criancaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Crianca> buscarAtivasPorFamilia(Long familiaId) {
        return jpaCriancaRepository.findByFamiliaIdAndAtivaTrue(familiaId).stream()
                .map(criancaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existe(Long id) {
        return jpaCriancaRepository.existsById(id);
    }

    @Override
    public void excluir(Long id) {
        jpaCriancaRepository.deleteById(id);
    }
}
