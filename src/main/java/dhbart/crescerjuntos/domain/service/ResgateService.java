package dhbart.crescerjuntos.domain.service;

import dhbart.crescerjuntos.domain.exception.PontosInsuficientesException;
import dhbart.crescerjuntos.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.domain.model.Resgate;
import dhbart.crescerjuntos.domain.model.StatusResgate;
import dhbart.crescerjuntos.infrastructure.persistence.JpaResgateRepository;
import dhbart.crescerjuntos.infrastructure.persistence.JpaCriancaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.JpaRecompensaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.ResgateEntityMapper;
import dhbart.crescerjuntos.infrastructure.persistence.entity.ResgateEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.RecompensaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResgateService {

    private final JpaResgateRepository resgateRepository;
    private final ResgateEntityMapper resgateMapper;
    private final JpaCriancaRepository criancaRepository;
    private final JpaRecompensaRepository recompensaRepository;
    private final ExecucaoTarefaService execucaoTarefaService;

    public Resgate solicitar(Resgate resgate) {
        CriancaEntity criancaEntity = criancaRepository.findById(resgate.getCriancaId())
                .orElseThrow(() -> ResourceNotFoundException.crianca(resgate.getCriancaId()));
        RecompensaEntity recompensaEntity = recompensaRepository.findById(resgate.getRecompensaId())
                .orElseThrow(() -> ResourceNotFoundException.recompensa(resgate.getRecompensaId()));
        int pontosDisponiveis = execucaoTarefaService.somarPontosAprovados(resgate.getCriancaId());
        if (pontosDisponiveis < resgate.getPontosUtilizados()) {
            throw new PontosInsuficientesException(
                    pontosDisponiveis,
                    resgate.getPontosUtilizados()
            );

        }
        ResgateEntity entity = resgateMapper.toEntity(resgate, criancaEntity, recompensaEntity);
        ResgateEntity savedEntity = resgateRepository.save(entity);
        return resgateMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public Resgate buscarPorId(Long id) {
        ResgateEntity entity = resgateRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.resgate(id));
        return resgateMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public List<Resgate> listarPorCrianca(Long criancaId) {
        return resgateRepository.findByCriancaId(criancaId).stream()
                .map(resgateMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Resgate> listarPorCriancaEStatus(Long criancaId, StatusResgate status) {
        return resgateRepository.findByCriancaIdAndStatus(criancaId, status).stream()
                .map(resgateMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Resgate aprovar(Long id) {
        ResgateEntity entity = resgateRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.resgate(id));
        Resgate resgate = resgateMapper.toDomain(entity);
        resgate.aprovar();
        entity.setStatus(resgate.getStatus());
        ResgateEntity savedEntity = resgateRepository.save(entity);
        return resgateMapper.toDomain(savedEntity);
    }

    public Resgate rejeitar(Long id, String motivo) {
        ResgateEntity entity = resgateRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.resgate(id));
        Resgate resgate = resgateMapper.toDomain(entity);
        resgate.rejeitar(motivo);
        entity.setStatus(resgate.getStatus());
        entity.setObservacao(resgate.getObservacao());
        ResgateEntity savedEntity = resgateRepository.save(entity);
        return resgateMapper.toDomain(savedEntity);
    }

    public Resgate entregar(Long id) {
        ResgateEntity entity = resgateRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.resgate(id));
        Resgate resgate = resgateMapper.toDomain(entity);
        resgate.entregar();
        entity.setStatus(resgate.getStatus());
        ResgateEntity savedEntity = resgateRepository.save(entity);
        return resgateMapper.toDomain(savedEntity);
    }

    public void excluir(Long id) {
        if (!resgateRepository.existsById(id)) {
            throw ResourceNotFoundException.resgate(id);
        }
        resgateRepository.deleteById(id);
    }
}
