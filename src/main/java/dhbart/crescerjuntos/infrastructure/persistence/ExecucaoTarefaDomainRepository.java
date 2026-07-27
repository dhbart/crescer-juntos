package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.domain.model.StatusExecucao;
import dhbart.crescerjuntos.domain.repository.ExecucaoTarefaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.ExecucaoTarefaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ExecucaoTarefaDomainRepository implements ExecucaoTarefaRepository {

    private final JpaExecucaoTarefaRepository jpaRepo;
    private final ExecucaoTarefaEntityMapper mapper;
    private final JpaTarefaRepository jpaTarefaRepo;
    private final JpaCriancaRepository jpaCriancaRepo;

    @Override
    public ExecucaoTarefa salvar(ExecucaoTarefa execucao) {
        var tarefaEntity = execucao.getTarefaId() != null ? jpaTarefaRepo.findById(execucao.getTarefaId()).orElse(null) : null;
        var criancaEntity = execucao.getCriancaId() != null ? jpaCriancaRepo.findById(execucao.getCriancaId()).orElse(null) : null;
        var entity = mapper.toEntity(execucao, tarefaEntity, criancaEntity);
        var saved = jpaRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ExecucaoTarefa> buscarPorId(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ExecucaoTarefa> buscarPorCrianca(Long criancaId) {
        return jpaRepo.findByCriancaId(criancaId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ExecucaoTarefa> buscarPorCriancaEStatus(Long criancaId, StatusExecucao status) {
        return jpaRepo.findByCriancaId(criancaId).stream()
                .filter(e -> e.getStatus() == status)
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<ExecucaoTarefa> buscarPorTarefaCriancaEData(Long tarefaId, Long criancaId, LocalDate data) {
        return jpaRepo.findByTarefaIdAndCriancaIdAndDataExecucao(tarefaId, criancaId, data).map(mapper::toDomain);
    }

    @Override
    public boolean existeExecucao(Long tarefaId, Long criancaId, LocalDate data) {
        return jpaRepo.findByTarefaIdAndCriancaIdAndDataExecucao(tarefaId, criancaId, data).isPresent();
    }

    @Override
    public void excluir(Long id) {
        jpaRepo.deleteById(id);
    }
}
