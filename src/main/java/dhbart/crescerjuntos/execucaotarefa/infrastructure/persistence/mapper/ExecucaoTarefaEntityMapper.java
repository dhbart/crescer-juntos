package dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.execucaotarefa.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence.entity.ExecucaoTarefaEntity;
import dhbart.crescerjuntos.tarefa.infrastructure.persistence.entity.TarefaEntity;
import org.springframework.stereotype.Component;

@Component
public class ExecucaoTarefaEntityMapper {

    public ExecucaoTarefaEntity toEntity(ExecucaoTarefa domain, TarefaEntity tarefa, CriancaEntity crianca) {
        if (domain == null) return null;
        return ExecucaoTarefaEntity.builder()
                .id(domain.getId())
                .tarefa(tarefa)
                .crianca(crianca)
                .dataExecucao(domain.getDataExecucao())
                .status(domain.getStatus())
                .pontosAplicados(domain.getPontosAplicados())
                .observacao(domain.getObservacao())
                .build();
    }

    public ExecucaoTarefa toDomain(ExecucaoTarefaEntity entity) {
        if (entity == null) return null;
        return ExecucaoTarefa.builder()
                .id(entity.getId())
                .tarefaId(entity.getTarefa().getId())
                .criancaId(entity.getCrianca().getId())
                .dataExecucao(entity.getDataExecucao())
                .status(entity.getStatus())
                .pontosAplicados(entity.getPontosAplicados())
                .observacao(entity.getObservacao())
                .build();
    }
}