package dhbart.crescerjuntos.tarefa.application.dto;

import dhbart.crescerjuntos.tarefa.domain.model.Frequencia;
import dhbart.crescerjuntos.tarefa.domain.model.Tarefa;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class TarefaResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private Frequencia frequencia;
    private List<DayOfWeek> diasDaSemana;
    private Integer diaDoMes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horarioPreferido;
    private int pontos;
    private boolean ativa;
    private Long familiaId;

    public static TarefaResponse from(Tarefa tarefa) {
        return TarefaResponse.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .frequencia(tarefa.getFrequencia())
                .diasDaSemana(tarefa.getDiasDaSemana())
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