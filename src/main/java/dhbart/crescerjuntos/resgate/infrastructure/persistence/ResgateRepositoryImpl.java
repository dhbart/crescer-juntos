package dhbart.crescerjuntos.resgate.infrastructure.persistence;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.JpaCriancaRepository;
import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import dhbart.crescerjuntos.recompensa.infrastructure.persistence.JpaRecompensaRepository;
import dhbart.crescerjuntos.resgate.domain.model.Resgate;
import dhbart.crescerjuntos.resgate.domain.repository.ResgateRepository;
import dhbart.crescerjuntos.resgate.infrastructure.persistence.mapper.ResgateEntityMapper;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResgateRepositoryImpl implements ResgateRepository {

    private final JpaResgateRepository jpaResgateRepository;
    private final ResgateEntityMapper mapper;
    private final JpaCriancaRepository jpaCriancaRepository;
    private final JpaRecompensaRepository jpaRecompensaRepository;

    @Override
    public Resgate salvar(Resgate resgate) {
        var crianca = jpaCriancaRepository.findById(resgate.getCriancaId())
                .orElseThrow(() -> ResourceNotFoundException.crianca(resgate.getCriancaId()));
        var recompensa = jpaRecompensaRepository.findById(resgate.getRecompensaId())
                .orElseThrow(() -> ResourceNotFoundException.recompensa(resgate.getRecompensaId()));
        var entity = mapper.toEntity(resgate, crianca, recompensa);
        var saved = jpaResgateRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Resgate> buscarPorId(Long id) {
        return jpaResgateRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Resgate> buscarPorCrianca(Long criancaId) {
        return jpaResgateRepository.findByCriancaId(criancaId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Resgate> buscarPorCriancaEStatus(Long criancaId, StatusResgate status) {
        return jpaResgateRepository.findByCriancaIdAndStatus(criancaId, status).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existe(Long id) { return jpaResgateRepository.existsById(id); }

    @Override
    public void excluir(Long id) { jpaResgateRepository.deleteById(id); }
}