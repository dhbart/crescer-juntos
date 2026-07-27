package dhbart.crescerjuntos.application.dto.execucao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AprovarExecucaoTarefaDTO {

    @Min(value = 0, message = "Pontos não podem ser negativos")
    @Max(value = 100, message = "Pontos máximos: 100")
    private int pontos;
}
