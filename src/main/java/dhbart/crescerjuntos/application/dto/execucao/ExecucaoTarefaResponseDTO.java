package dhbart.crescerjuntos.application.dto.execucao;

import dhbart.crescerjuntos.domain.model.StatusExecucao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecucaoTarefaResponseDTO {

    private Long id;
    private Long tarefaId;
    private Long criancaId;
    private LocalDate dataExecucao;
    private StatusExecucao status;
    private int pontosAplicados;
    private String observacao;
}
