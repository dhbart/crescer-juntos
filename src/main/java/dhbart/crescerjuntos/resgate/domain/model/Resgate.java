package dhbart.crescerjuntos.resgate.domain.model;

import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import lombok.*;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Resgate {

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private Long criancaId;

    @Setter(AccessLevel.NONE)
    private Long recompensaId;

    @Builder.Default
    private StatusResgate status = StatusResgate.SOLICITADO;

    private int pontosUtilizados;
    private String observacao;

    public Resgate(Long criancaId, Long recompensaId, int pontosUtilizados) {
        this.id = null;
        this.criancaId = Objects.requireNonNull(criancaId, "ID da criança é obrigatório");
        this.recompensaId = Objects.requireNonNull(recompensaId, "ID da recompensa é obrigatório");
        this.status = StatusResgate.SOLICITADO;
        if (pontosUtilizados <= 0) {
            throw new IllegalArgumentException("Pontos utilizados devem ser maiores que zero");
        }
        this.pontosUtilizados = pontosUtilizados;
        this.observacao = null;
    }

    public void aprovar() {
        exigirStatus(StatusResgate.SOLICITADO, "aprovar");
        this.status = StatusResgate.APROVADO;
    }

    public void rejeitar(String motivo) {
        exigirStatus(StatusResgate.SOLICITADO, "rejeitar");
        this.status = StatusResgate.REJEITADO;
        this.observacao = motivo;
    }

    public void entregar() {
        exigirStatus(StatusResgate.APROVADO, "entregar");
        this.status = StatusResgate.ENTREGUE;
    }

    public boolean isSolicitado() {
        return status == StatusResgate.SOLICITADO;
    }

    public boolean isAprovado() {
        return status == StatusResgate.APROVADO;
    }

    private void exigirStatus(StatusResgate esperado, String operacao) {
        if (status != esperado) {
            throw new IllegalStateException("Só é possível " + operacao + " resgates " + esperado.name().toLowerCase());
        }
    }
}