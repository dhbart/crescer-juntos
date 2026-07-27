package dhbart.crescerjuntos.infrastructure.persistence;

import dhbart.crescerjuntos.domain.model.Tarefa;
import dhbart.crescerjuntos.domain.repository.TarefaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.TarefaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TarefaDomainRepository implements TarefaRepository {

    private final JpaTarefaRepository jpaTarefaRepository;
    private final TarefaEntityMapper tarefaMapper;
    private final JpaFamiliaRepository jpaFamiliaRepository;

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        FamiliaEntity familiaEntity = jpaFamiliaRepository.findById(tarefa.getFamiliaId()).orElse(null);
        var entity = tarefaMapper.toEntity(tarefa, familiaEntity);
        var saved = jpaTarefaRepository.save(entity);
        return tarefaMapper.toDomain(saved);
    }

    @Override
    public Optional<Tarefa> buscarPorId(Long id) {
        return jpaTarefaRepository.findById(id).map(tarefaMapper::toDomain);
    }

    @Override
    public List<Tarefa> buscarPorFamilia(Long familiaId) {
        return jpaTarefaRepository.findByFamiliaId(familiaId).stream()
                .map(tarefaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> buscarAtivasPorFamilia(Long familiaId) {
        return jpaTarefaRepository.findByFamiliaIdAndAtivaTrue(familiaId).stream()
                .map(tarefaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> buscarPorCrianca(Long criancaId) {
        throw new UnsupportedOperationException("Not implemented via domain repository");
    }

    @Override
    public List<Tarefa> buscarAtivasNaData(Long familiaId, LocalDate data) {
        return jpaTarefaRepository.findAtivasNaData(familiaId, data).stream()
                .map(tarefaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existe(Long id) {
        return jpaTarefaRepository.existsById(id);
    }

    @Override
    public void excluir(Long id) {
        jpaTarefaRepository.deleteById(id);
    }
}
