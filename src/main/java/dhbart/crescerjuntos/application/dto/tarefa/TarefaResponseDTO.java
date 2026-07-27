package dhbart.crescerjuntos.application.dto.tarefa;

import dhbart.crescerjuntos.domain.model.Frequencia;
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
public class TarefaResponseDTO {

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
}
