package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.Resgate;
import dhbart.crescerjuntos.domain.model.StatusResgate;
import dhbart.crescerjuntos.domain.repository.ResgateRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.ResgateEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResgateDomainRepository implements ResgateRepository {

    private final JpaResgateRepository jpaRepo;
    private final ResgateEntityMapper mapper;
    private final JpaCriancaRepository jpaCriancaRepo;
    private final JpaRecompensaRepository jpaRecompensaRepo;

    @Override
    public Resgate salvar(Resgate resgate) {
        var criancaEntity = jpaCriancaRepo.findById(resgate.getCriancaId()).orElse(null);
        var recompensaEntity = jpaRecompensaRepo.findById(resgate.getRecompensaId()).orElse(null);
        var entity = mapper.toEntity(resgate, criancaEntity, recompensaEntity);
        var saved = jpaRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Resgate> buscarPorId(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Resgate> buscarPorCrianca(Long criancaId) {
        return jpaRepo.findByCriancaId(criancaId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Resgate> buscarPorCriancaEStatus(Long criancaId, StatusResgate status) {
        return jpaRepo.findByCriancaIdAndStatus(criancaId, status).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Resgate> buscarPorFamilia(Long familiaId) {
        throw new UnsupportedOperationException("Not implemented via domain repository");
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
