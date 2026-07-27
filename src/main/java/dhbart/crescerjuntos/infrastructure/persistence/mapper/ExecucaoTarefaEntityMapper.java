package dhbart.crescerjuntos.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.ExecucaoTarefaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.TarefaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecucaoTarefaEntityMapper {

    public ExecucaoTarefaEntity toEntity(ExecucaoTarefa domain, TarefaEntity tarefaEntity, CriancaEntity criancaEntity) {
        if (domain == null) {
            return null;
        }
        
        return ExecucaoTarefaEntity.builder()
                .id(domain.getId())
                .tarefa(tarefaEntity)
                .crianca(criancaEntity)
                .dataExecucao(domain.getDataExecucao())
                .status(domain.getStatus())
                .pontosAplicados(domain.getPontosAplicados())
                .observacao(domain.getObservacao())
                .build();
    }

    public ExecucaoTarefa toDomain(ExecucaoTarefaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return ExecucaoTarefa.builder()
                .id(entity.getId())
                .tarefaId(entity.getTarefa() != null ? entity.getTarefa().getId() : null)
                .criancaId(entity.getCrianca() != null ? entity.getCrianca().getId() : null)
                .dataExecucao(entity.getDataExecucao())
                .status(entity.getStatus())
                .pontosAplicados(entity.getPontosAplicados())
                .observacao(entity.getObservacao())
                .build();
    }
}