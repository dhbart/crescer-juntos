package dhbart.crescerjuntos.tarefa.infrastructure.persistence;

import dhbart.crescerjuntos.tarefa.domain.model.Tarefa;
import dhbart.crescerjuntos.tarefa.domain.repository.TarefaRepository;
import dhbart.crescerjuntos.tarefa.infrastructure.persistence.mapper.TarefaEntityMapper;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.JpaFamiliaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TarefaRepositoryImpl implements TarefaRepository {

    private final JpaTarefaRepository jpaTarefaRepository;
    private final TarefaEntityMapper tarefaEntityMapper;
    private final JpaFamiliaRepository jpaFamiliaRepository;

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        var familia = jpaFamiliaRepository.findById(tarefa.getFamiliaId())
                .orElseThrow(() -> ResourceNotFoundException.familia(tarefa.getFamiliaId()));
        var entity = tarefaEntityMapper.toEntity(tarefa, familia);
        var saved = jpaTarefaRepository.save(entity);
        return tarefaEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Tarefa> buscarPorId(Long id) {
        return jpaTarefaRepository.findById(id).map(tarefaEntityMapper::toDomain);
    }

    @Override
    public List<Tarefa> buscarPorFamilia(Long familiaId) {
        return jpaTarefaRepository.findByFamiliaId(familiaId).stream()
                .map(tarefaEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> buscarAtivasPorFamilia(Long familiaId) {
        return jpaTarefaRepository.findByFamiliaIdAndAtivaTrue(familiaId).stream()
                .map(tarefaEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> buscarAtivasNaData(Long familiaId, LocalDate data) {
        return jpaTarefaRepository.findAtivasNaData(familiaId, data).stream()
                .map(tarefaEntityMapper::toDomain).collect(Collectors.toList());
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