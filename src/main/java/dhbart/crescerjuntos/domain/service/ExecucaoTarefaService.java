package dhbart.crescerjuntos.domain.service;

import dhbart.crescerjuntos.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.infrastructure.persistence.JpaExecucaoTarefaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.ExecucaoTarefaEntityMapper;
import dhbart.crescerjuntos.infrastructure.persistence.entity.ExecucaoTarefaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.TarefaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.JpaTarefaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.JpaCriancaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ExecucaoTarefaService {

    private final JpaExecucaoTarefaRepository execucaoRepository;
    private final ExecucaoTarefaEntityMapper execucaoMapper;
    private final JpaTarefaRepository tarefaRepository;
    private final JpaCriancaRepository criancaRepository;

    public ExecucaoTarefa registrar(Long tarefaId, Long criancaId, LocalDate dataExecucao, String observacao) {
        TarefaEntity tarefaEntity = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> ResourceNotFoundException.tarefa(tarefaId));
        CriancaEntity criancaEntity = criancaRepository.findById(criancaId)
                .orElseThrow(() -> ResourceNotFoundException.crianca(criancaId));

        ExecucaoTarefa execucao = new ExecucaoTarefa(tarefaId, criancaId, dataExecucao);
        execucao.registrar(observacao);

        ExecucaoTarefaEntity entity = execucaoMapper.toEntity(execucao, tarefaEntity, criancaEntity);
        ExecucaoTarefaEntity savedEntity = execucaoRepository.save(entity);

        return execucaoMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public ExecucaoTarefa buscarPorId(Long id) {
        ExecucaoTarefaEntity entity = execucaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        return execucaoMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public List<ExecucaoTarefa> listarPorCrianca(Long criancaId) {
        return execucaoRepository.findByCriancaId(criancaId).stream()
                .map(execucaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExecucaoTarefa> listarPorCriancaEStatus(Long criancaId, dhbart.crescerjuntos.domain.model.StatusExecucao status) {
        return execucaoRepository.findByCriancaId(criancaId).stream()
                .filter(e -> e.getStatus() == status)
                .map(execucaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExecucaoTarefa> listarPorPeriodo(Long criancaId, LocalDate dataInicio, LocalDate dataFim) {
        return execucaoRepository.findByCriancaIdAndPeriodo(criancaId, dataInicio, dataFim).stream()
                .map(execucaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int somarPontosAprovados(Long criancaId) {
        Integer total = execucaoRepository.somarPontosPorCrianca(criancaId);
        return total != null ? total : 0;
    }

    public ExecucaoTarefa aprovar(Long id, int pontos) {
        ExecucaoTarefaEntity entity = execucaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        ExecucaoTarefa execucao = execucaoMapper.toDomain(entity);
        execucao.aprovar(pontos);
        entity.setStatus(execucao.getStatus());
        entity.setPontosAplicados(execucao.getPontosAplicados());
        ExecucaoTarefaEntity savedEntity = execucaoRepository.save(entity);
        return execucaoMapper.toDomain(savedEntity);
    }

    public ExecucaoTarefa rejeitar(Long id, String motivo) {
        ExecucaoTarefaEntity entity = execucaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        ExecucaoTarefa execucao = execucaoMapper.toDomain(entity);
        execucao.rejeitar(motivo);
        entity.setStatus(execucao.getStatus());
        entity.setObservacao(execucao.getObservacao());
        ExecucaoTarefaEntity savedEntity = execucaoRepository.save(entity);
        return execucaoMapper.toDomain(savedEntity);
    }

    public ExecucaoTarefa expirar(Long id) {
        ExecucaoTarefaEntity entity = execucaoRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.execucaoTarefa(id));
        ExecucaoTarefa execucao = execucaoMapper.toDomain(entity);
        execucao.expirar();
        entity.setStatus(execucao.getStatus());
        ExecucaoTarefaEntity savedEntity = execucaoRepository.save(entity);
        return execucaoMapper.toDomain(savedEntity);
    }

    public void excluir(Long id) {
        if (!execucaoRepository.existsById(id)) {
            throw ResourceNotFoundException.execucaoTarefa(id);
        }
        execucaoRepository.deleteById(id);
    }
}
