package dhbart.crescerjuntos.application.dto.execucao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecucaoTarefaCreateDTO {

    @NotNull(message = "ID da tarefa é obrigatório")
    private Long tarefaId;

    @NotNull(message = "ID da criança é obrigatório")
    private Long criancaId;

    @NotNull(message = "Data de execução é obrigatória")
    private LocalDate dataExecucao;

    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    private String observacao;
}
