package dhbart.crescerjuntos.domain.service;

import dhbart.crescerjuntos.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.domain.model.Recompensa;
import dhbart.crescerjuntos.infrastructure.persistence.JpaRecompensaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.JpaFamiliaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.RecompensaEntityMapper;
import dhbart.crescerjuntos.infrastructure.persistence.entity.RecompensaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
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
public class RecompensaService {

    private final JpaRecompensaRepository recompensaRepository;
    private final RecompensaEntityMapper recompensaMapper;
    private final JpaFamiliaRepository familiaRepository;

    public Recompensa criar(Recompensa recompensa) {
        FamiliaEntity familiaEntity = familiaRepository.findById(recompensa.getFamiliaId())
                .orElseThrow(() -> ResourceNotFoundException.familia(recompensa.getFamiliaId()));
        RecompensaEntity entity = recompensaMapper.toEntity(recompensa, familiaEntity);
        RecompensaEntity savedEntity = recompensaRepository.save(entity);
        return recompensaMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public Recompensa buscarPorId(Long id) {
        RecompensaEntity entity = recompensaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.recompensa(id));
        return recompensaMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public List<Recompensa> listarPorFamilia(Long familiaId) {
        return recompensaRepository.findByFamiliaId(familiaId).stream()
                .map(recompensaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Recompensa> listarDisponiveisPorFamilia(Long familiaId) {
        return recompensaRepository.findByFamiliaIdAndDisponivelTrue(familiaId).stream()
                .map(recompensaMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Recompensa atualizar(Long id, String nome, String descricao, int custoPontos) {
        RecompensaEntity entity = recompensaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.recompensa(id));
        Recompensa recompensa = recompensaMapper.toDomain(entity);
        recompensa.setNome(nome);
        recompensa.setDescricao(descricao);
        recompensa.atualizarCusto(custoPontos);
        entity.setNome(recompensa.getNome());
        entity.setDescricao(recompensa.getDescricao());
        entity.setCustoPontos(recompensa.getCustoPontos());
        RecompensaEntity savedEntity = recompensaRepository.save(entity);
        return recompensaMapper.toDomain(savedEntity);
    }

    public void ativar(Long id) {
        RecompensaEntity entity = recompensaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.recompensa(id));
        Recompensa recompensa = recompensaMapper.toDomain(entity);
        recompensa.ativar();
        entity.setDisponivel(recompensa.isDisponivel());
        recompensaRepository.save(entity);
    }

    public void desativar(Long id) {
        RecompensaEntity entity = recompensaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.recompensa(id));
        Recompensa recompensa = recompensaMapper.toDomain(entity);
        recompensa.desativar();
        entity.setDisponivel(recompensa.isDisponivel());
        recompensaRepository.save(entity);
    }

    public void excluir(Long id) {
        if (!recompensaRepository.existsById(id)) {
            throw ResourceNotFoundException.recompensa(id);
        }
        recompensaRepository.deleteById(id);
    }
}
