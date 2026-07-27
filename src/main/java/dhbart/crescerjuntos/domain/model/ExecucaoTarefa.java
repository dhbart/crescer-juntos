package dhbart.crescerjuntos.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExecucaoTarefa {

    private static final int PONTOS_MAXIMOS = 100;

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private Long tarefaId;

    @Setter(AccessLevel.NONE)
    private Long criancaId;
    @Setter(AccessLevel.NONE)
    private LocalDate dataExecucao;
    
    @Builder.Default
    private StatusExecucao status = StatusExecucao.PENDENTE;
    
    @Builder.Default
    private int pontosAplicados = 0;
    
    private String observacao;

    public ExecucaoTarefa(Long tarefaId, Long criancaId, LocalDate dataExecucao) {
        this.id = null;
        this.tarefaId = Objects.requireNonNull(tarefaId, "ID da tarefa é obrigatório");
        this.criancaId = Objects.requireNonNull(criancaId, "ID da criança é obrigatório");
        this.dataExecucao = Objects.requireNonNull(dataExecucao, "Data de execução é obrigatória");
        this.status = StatusExecucao.PENDENTE;
        this.pontosAplicados = 0;
        this.observacao = null;
    }

    public void registrar(String observacao) {
        exigirStatus(StatusExecucao.PENDENTE, "registrar");
        this.status = StatusExecucao.REGISTRADA;
        this.observacao = observacao;
    }

    public void aprovar(int pontos) {
        exigirStatus(StatusExecucao.REGISTRADA, "aprovar");
        if (pontos < 0 || pontos > PONTOS_MAXIMOS) {
            throw new IllegalArgumentException("Pontos devem estar entre 0 e " + PONTOS_MAXIMOS);
        }
        this.status = StatusExecucao.APROVADA;
        this.pontosAplicados = pontos;
    }

    public void rejeitar(String motivo) {
        exigirStatus(StatusExecucao.REGISTRADA, "rejeitar");
        this.status = StatusExecucao.REJEITADA;
        this.observacao = motivo;
    }

    public void expirar() {
        exigirStatus(StatusExecucao.PENDENTE, "expirar");
        this.status = StatusExecucao.EXPIRADA;
    }

    public boolean isPendente() {
        return status == StatusExecucao.PENDENTE;
    }

    public boolean isAprovada() {
        return status == StatusExecucao.APROVADA;
    }

    private void exigirStatus(StatusExecucao esperado, String operacao) {
        if (status != esperado) {
            throw new IllegalStateException("Só é possível " + operacao + " execuções " + esperado.name().toLowerCase());
        }
    }
}