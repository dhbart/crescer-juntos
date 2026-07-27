package dhbart.crescerjuntos.application.dto.mapper;

import dhbart.crescerjuntos.application.dto.tarefa.TarefaCreateDTO;
import dhbart.crescerjuntos.application.dto.tarefa.TarefaResponseDTO;
import dhbart.crescerjuntos.domain.model.Tarefa;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TarefaMapper {

    public Tarefa toDomain(TarefaCreateDTO dto) {
        Tarefa tarefa = new Tarefa(
                dto.getTitulo(),
                dto.getFrequencia(),
                dto.getFamiliaId(),
                dto.getPontos()
        );

        tarefa.setDescricao(dto.getDescricao());
        tarefa.setHorarioPreferido(dto.getHorarioPreferido());

        if (dto.getFrequencia() == dhbart.crescerjuntos.domain.model.Frequencia.SEMANAL
                && dto.getDiasDaSemana() != null) {
            tarefa.configurarDiasDaSemana(dto.getDiasDaSemana());
        }

        if (dto.getFrequencia() == dhbart.crescerjuntos.domain.model.Frequencia.MENSAL
                && dto.getDiaDoMes() != null) {
            tarefa.configurarDiaDoMes(dto.getDiaDoMes());
        }

        if (dto.getFrequencia() == dhbart.crescerjuntos.domain.model.Frequencia.PERIODO_PERSONALIZADO) {
            tarefa.configurarPeriodoPersonalizado(
                    dto.getDataInicio(),
                    dto.getDataFim()
            );
        }

        return tarefa;
    }

    public TarefaResponseDTO toResponse(Tarefa tarefa) {
        return TarefaResponseDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .frequencia(tarefa.getFrequencia())
                .diasDaSemana(new ArrayList<>(tarefa.getDiasDaSemana()))
                .diaDoMes(tarefa.getDiaDoMes())
                .dataInicio(tarefa.getDataInicio())
                .dataFim(tarefa.getDataFim())
                .horarioPreferido(tarefa.getHorarioPreferido())
                .pontos(tarefa.getPontos())
                .ativa(tarefa.isAtiva())
                .familiaId(tarefa.getFamiliaId())
                .build();
    }
}
