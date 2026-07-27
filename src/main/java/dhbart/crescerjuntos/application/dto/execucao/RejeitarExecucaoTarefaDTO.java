package dhbart.crescerjuntos.application.dto.execucao;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RejeitarExecucaoTarefaDTO {

    @Size(max = 500, message = "Motivo deve ter no máximo 500 caracteres")
    private String motivo;
}
