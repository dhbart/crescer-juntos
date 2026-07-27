package dhbart.crescerjuntos.domain.service;

import dhbart.crescerjuntos.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.domain.model.Frequencia;
import dhbart.crescerjuntos.domain.model.Tarefa;
import dhbart.crescerjuntos.infrastructure.persistence.JpaFamiliaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.JpaTarefaRepository;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.TarefaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.mapper.TarefaEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TarefaService {

    private final JpaTarefaRepository tarefaRepository;
    private final TarefaEntityMapper tarefaMapper;
    private final JpaFamiliaRepository familiaRepository;

    public Tarefa criar(Tarefa tarefa) {
        FamiliaEntity familiaEntity = familiaRepository.findById(tarefa.getFamiliaId())
                .orElseThrow(() -> ResourceNotFoundException.familia(tarefa.getFamiliaId()));
        TarefaEntity entity = tarefaMapper.toEntity(tarefa, familiaEntity);
        TarefaEntity savedEntity = tarefaRepository.save(entity);
        return tarefaMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public Tarefa buscarPorId(Long id) {
        TarefaEntity entity = tarefaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.tarefa(id));
        return tarefaMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarPorFamilia(Long familiaId) {
        return tarefaRepository.findByFamiliaId(familiaId).stream()
                .map(tarefaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarAtivasPorFamilia(Long familiaId) {
        return tarefaRepository.findByFamiliaIdAndAtivaTrue(familiaId).stream()
                .map(tarefaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarAtivasNaData(Long familiaId, LocalDate data) {
        return tarefaRepository.findAtivasNaData(familiaId, data).stream()
                .map(tarefaMapper::toDomain)
                .collect(Collectors.toList());
    }

    public void ativar(Long id) {
        TarefaEntity entity = tarefaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.tarefa(id));
        Tarefa tarefa = tarefaMapper.toDomain(entity);
        tarefa.ativar();
        entity.setAtiva(tarefa.isAtiva());
        tarefaRepository.save(entity);
    }

    public void desativar(Long id) {
        TarefaEntity entity = tarefaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.tarefa(id));
        Tarefa tarefa = tarefaMapper.toDomain(entity);
        tarefa.desativar();
        entity.setAtiva(tarefa.isAtiva());
        tarefaRepository.save(entity);
    }


    public Tarefa atualizar(Long id, String titulo, String descricao, Frequencia frequencia,
                              List<DayOfWeek> diasDaSemana, Integer diaDoMes,
                              LocalDate dataInicio, LocalDate dataFim, LocalTime horarioPreferido,
                              int pontos) {
        TarefaEntity entity = tarefaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.tarefa(id));
        Tarefa tarefa = tarefaMapper.toDomain(entity);
        tarefa.atualizarDados(titulo, descricao, frequencia, diasDaSemana, diaDoMes,
                              dataInicio, dataFim, horarioPreferido, pontos);
        entity.setTitulo(tarefa.getTitulo());
        entity.setDescricao(tarefa.getDescricao());
        entity.setFrequencia(tarefa.getFrequencia());
        entity.setDiasDaSemana(new ArrayList<>(tarefa.getDiasDaSemana()));
        entity.setDiaDoMes(tarefa.getDiaDoMes());
        entity.setDataInicio(tarefa.getDataInicio());
        entity.setDataFim(tarefa.getDataFim());
        entity.setHorarioPreferido(tarefa.getHorarioPreferido());
        entity.setPontos(tarefa.getPontos());
        TarefaEntity savedEntity = tarefaRepository.save(entity);
        return tarefaMapper.toDomain(savedEntity);
    }

    public void excluir(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw ResourceNotFoundException.tarefa(id);
        }
        tarefaRepository.deleteById(id);
    }
}
