package dhbart.crescerjuntos.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.domain.model.Tarefa;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.TarefaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class TarefaEntityMapper {

    public TarefaEntity toEntity(Tarefa domain, FamiliaEntity familiaEntity) {
        if (domain == null) {
            return null;
        }
        
        return TarefaEntity.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .descricao(domain.getDescricao())
                .frequencia(domain.getFrequencia())
                .diasDaSemana(new ArrayList<>(domain.getDiasDaSemana()))
                .diaDoMes(domain.getDiaDoMes())
                .dataInicio(domain.getDataInicio())
                .dataFim(domain.getDataFim())
                .horarioPreferido(domain.getHorarioPreferido())
                .pontos(domain.getPontos())
                .ativa(domain.isAtiva())
                .familia(familiaEntity)
                .build();
    }

    public Tarefa toDomain(TarefaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Tarefa.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .descricao(entity.getDescricao())
                .frequencia(entity.getFrequencia())
                .diasDaSemana(new ArrayList<>(entity.getDiasDaSemana()))
                .diaDoMes(entity.getDiaDoMes())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .horarioPreferido(entity.getHorarioPreferido())
                .pontos(entity.getPontos())
                .ativa(entity.isAtiva())
                .familiaId(entity.getFamilia() != null ? entity.getFamilia().getId() : null)
                .build();
    }
}