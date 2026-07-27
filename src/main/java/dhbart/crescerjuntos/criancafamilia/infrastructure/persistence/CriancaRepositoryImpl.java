package dhbart.crescerjuntos.criancafamilia.infrastructure.persistence;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.mapper.CriancaEntityMapper;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CriancaRepositoryImpl implements CriancaRepository {

    private final JpaCriancaRepository jpaCriancaRepository;
    private final CriancaEntityMapper criancaEntityMapper;
    private final JpaFamiliaRepository jpaFamiliaRepository;

    @Override
    public Crianca salvar(Crianca crianca) {
        var familiaEntity = jpaFamiliaRepository.findById(crianca.getFamiliaId())
                .orElseThrow(() -> ResourceNotFoundException.familia(crianca.getFamiliaId()));
        var entity = criancaEntityMapper.toEntity(crianca, familiaEntity);
        var saved = jpaCriancaRepository.save(entity);
        return criancaEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Crianca> buscarPorId(Long id) {
        return jpaCriancaRepository.findById(id).map(criancaEntityMapper::toDomain);
    }

    @Override
    public List<Crianca> buscarPorFamilia(Long familiaId) {
        return jpaCriancaRepository.findByFamiliaId(familiaId).stream()
                .map(criancaEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Crianca> buscarAtivasPorFamilia(Long familiaId) {
        return jpaCriancaRepository.findByFamiliaIdAndAtivaTrue(familiaId).stream()
                .map(criancaEntityMapper::toDomain).collect(Collectors.toList());
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