package dhbart.crescerjuntos.application.dto.tarefa;

import dhbart.crescerjuntos.domain.model.Frequencia;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaUpdateDTO {

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

    @Min(value = 0, message = "Pontos não podem ser negativos")
    @Max(value = 100, message = "Pontos máximos: 100")
    private int pontos;
}
