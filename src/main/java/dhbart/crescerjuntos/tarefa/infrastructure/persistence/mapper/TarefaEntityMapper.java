package dhbart.crescerjuntos.tarefa.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.tarefa.domain.model.Tarefa;
import dhbart.crescerjuntos.tarefa.infrastructure.persistence.entity.TarefaEntity;
import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.FamiliaEntity;
import org.springframework.stereotype.Component;

@Component
public class TarefaEntityMapper {

    public TarefaEntity toEntity(Tarefa domain, FamiliaEntity familia) {
        if (domain == null) return null;
        return TarefaEntity.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .descricao(domain.getDescricao())
                .frequencia(domain.getFrequencia())
                .diasDaSemana(domain.getDiasDaSemana())
                .diaDoMes(domain.getDiaDoMes())
                .dataInicio(domain.getDataInicio())
                .dataFim(domain.getDataFim())
                .horarioPreferido(domain.getHorarioPreferido())
                .pontos(domain.getPontos())
                .ativa(domain.isAtiva())
                .familia(familia)
                .build();
    }

    public Tarefa toDomain(TarefaEntity entity) {
        if (entity == null) return null;
        return Tarefa.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .descricao(entity.getDescricao())
                .frequencia(entity.getFrequencia())
                .diasDaSemana(entity.getDiasDaSemana())
                .diaDoMes(entity.getDiaDoMes())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .horarioPreferido(entity.getHorarioPreferido())
                .pontos(entity.getPontos())
                .ativa(entity.isAtiva())
                .familiaId(entity.getFamilia().getId())
                .build();
    }
}