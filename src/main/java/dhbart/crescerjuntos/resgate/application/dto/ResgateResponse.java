package dhbart.crescerjuntos.resgate.application.dto;

import dhbart.crescerjuntos.resgate.domain.model.Resgate;
import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResgateResponse {
    private Long id;
    private Long criancaId;
    private Long recompensaId;
    private StatusResgate status;
    private int pontosUtilizados;
    private String observacao;

    public static ResgateResponse from(Resgate r) {
        return ResgateResponse.builder().id(r.getId()).criancaId(r.getCriancaId())
                .recompensaId(r.getRecompensaId()).status(r.getStatus())
                .pontosUtilizados(r.getPontosUtilizados()).observacao(r.getObservacao()).build();
    }
}