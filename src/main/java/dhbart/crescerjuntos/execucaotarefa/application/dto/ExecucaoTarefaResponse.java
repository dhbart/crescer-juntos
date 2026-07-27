package dhbart.crescerjuntos.execucaotarefa.application.dto;

import dhbart.crescerjuntos.execucaotarefa.domain.model.StatusExecucao;
import dhbart.crescerjuntos.execucaotarefa.domain.model.ExecucaoTarefa;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ExecucaoTarefaResponse {
    private Long id;
    private Long tarefaId;
    private Long criancaId;
    private LocalDate dataExecucao;
    private StatusExecucao status;
    private int pontosAplicados;
    private String observacao;

    public static ExecucaoTarefaResponse from(ExecucaoTarefa e) {
        return ExecucaoTarefaResponse.builder().id(e.getId()).tarefaId(e.getTarefaId())
                .criancaId(e.getCriancaId()).dataExecucao(e.getDataExecucao())
                .status(e.getStatus()).pontosAplicados(e.getPontosAplicados())
                .observacao(e.getObservacao()).build();
    }
}