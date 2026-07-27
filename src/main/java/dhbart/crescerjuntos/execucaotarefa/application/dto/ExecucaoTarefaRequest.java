package dhbart.crescerjuntos.execucaotarefa.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ExecucaoTarefaRequest {
    @NotNull private Long tarefaId;
    @NotNull private Long criancaId;
    @NotNull private LocalDate dataExecucao;
    private String observacao;
}