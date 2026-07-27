package dhbart.crescerjuntos.domain.service;

import dhbart.crescerjuntos.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.domain.model.Familia;
import dhbart.crescerjuntos.infrastructure.persistence.JpaFamiliaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.FamiliaEntityMapper;
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
public class FamiliaService {

    private final JpaFamiliaRepository familiaRepository;
    private final FamiliaEntityMapper familiaMapper;

    public Familia criar(Familia familia) {
        log.info("Criando nova família: {}", familia.getNome());

        FamiliaEntity entity = familiaMapper.toEntity(familia);
        FamiliaEntity savedEntity = familiaRepository.save(entity);

        return familiaMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public Familia buscarPorId(Long id) {
        FamiliaEntity entity = familiaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));

        return familiaMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public List<Familia> listarTodas() {
        return familiaRepository.findAll()
                .stream()
                .map(familiaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Familia> listarAtivas() {
        return familiaRepository.findByAtivaTrue()
                .stream()
                .map(familiaMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Familia atualizar(Long id, String novoNome) {
        FamiliaEntity entity = familiaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));

        Familia familia = familiaMapper.toDomain(entity);
        familia.atualizarNome(novoNome);

        entity.setNome(familia.getNome());

        // atualizadoEm é gerenciado pelo @UpdateTimestamp da entity.
        FamiliaEntity savedEntity = familiaRepository.save(entity);

        return familiaMapper.toDomain(savedEntity);
    }

    public void ativar(Long id) {
        FamiliaEntity entity = familiaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));

        Familia familia = familiaMapper.toDomain(entity);
        familia.ativar();

        entity.setAtiva(familia.isAtiva());

        // atualizadoEm é gerenciado pelo @UpdateTimestamp da entity.
        familiaRepository.save(entity);
    }

    public void desativar(Long id) {
        FamiliaEntity entity = familiaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.familia(id));

        Familia familia = familiaMapper.toDomain(entity);
        familia.desativar();

        entity.setAtiva(familia.isAtiva());

        // atualizadoEm é gerenciado pelo @UpdateTimestamp da entity.
        familiaRepository.save(entity);
    }

    public void excluir(Long id) {
        if (!familiaRepository.existsById(id)) {
            throw ResourceNotFoundException.familia(id);
        }

        familiaRepository.deleteById(id);
    }
}
