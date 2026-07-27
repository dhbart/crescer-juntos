package dhbart.crescerjuntos.tarefa.application.dto;

import dhbart.crescerjuntos.tarefa.domain.model.Frequencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class TarefaRequest {
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    private String descricao;
    @NotNull(message = "Frequência é obrigatória")
    private Frequencia frequencia;
    private List<DayOfWeek> diasDaSemana;
    private Integer diaDoMes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horarioPreferido;
    private int pontos;
    @NotNull(message = "ID da família é obrigatório")
    private Long familiaId;
}