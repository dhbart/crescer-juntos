package dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.JpaCriancaRepository;
import dhbart.crescerjuntos.execucaotarefa.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.execucaotarefa.domain.repository.ExecucaoTarefaRepository;
import dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence.mapper.ExecucaoTarefaEntityMapper;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.tarefa.infrastructure.persistence.JpaTarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ExecucaoTarefaRepositoryImpl implements ExecucaoTarefaRepository {

    private final JpaExecucaoTarefaRepository jpaExecucaoRepository;
    private final ExecucaoTarefaEntityMapper mapper;
    private final JpaTarefaRepository jpaTarefaRepository;
    private final JpaCriancaRepository jpaCriancaRepository;

    @Override
    public ExecucaoTarefa salvar(ExecucaoTarefa execucao) {
        var tarefaEntity = jpaTarefaRepository.findById(execucao.getTarefaId())
                .orElseThrow(() -> ResourceNotFoundException.tarefa(execucao.getTarefaId()));
        var criancaEntity = jpaCriancaRepository.findById(execucao.getCriancaId())
                .orElseThrow(() -> ResourceNotFoundException.crianca(execucao.getCriancaId()));
        var entity = mapper.toEntity(execucao, tarefaEntity, criancaEntity);
        var saved = jpaExecucaoRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ExecucaoTarefa> buscarPorId(Long id) {
        return jpaExecucaoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ExecucaoTarefa> buscarPorCrianca(Long criancaId) {
        return jpaExecucaoRepository.findByCriancaId(criancaId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existeExecucao(Long tarefaId, Long criancaId, LocalDate data) {
        return jpaExecucaoRepository.existsByTarefaIdAndCriancaIdAndDataExecucao(tarefaId, criancaId, data);
    }

    @Override
    public void excluir(Long id) {
        jpaExecucaoRepository.deleteById(id);
    }
}