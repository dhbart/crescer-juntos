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
public class TarefaCreateDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "Frequência é obrigatória")
    private Frequencia frequencia;

    private List<DayOfWeek> diasDaSemana;

    @Min(value = 1, message = "Dia do mês deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia do mês deve ser entre 1 e 31")
    private Integer diaDoMes;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horarioPreferido;

    @Min(value = 0, message = "Pontos não podem ser negativos")
    @Max(value = 100, message = "Pontos máximos: 100")
    private int pontos;

    @NotNull(message = "ID da família é obrigatório")
    private Long familiaId;
}
