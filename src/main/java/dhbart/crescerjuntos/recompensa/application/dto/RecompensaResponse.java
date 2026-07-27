package dhbart.crescerjuntos.recompensa.application.dto;

import dhbart.crescerjuntos.recompensa.domain.model.Recompensa;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecompensaResponse {
    private Long id;
    private String nome;
    private String descricao;
    private int custoPontos;
    private Long familiaId;
    private boolean disponivel;

    public static RecompensaResponse from(Recompensa r) {
        return RecompensaResponse.builder().id(r.getId()).nome(r.getNome())
                .descricao(r.getDescricao()).custoPontos(r.getCustoPontos())
                .familiaId(r.getFamiliaId()).disponivel(r.isDisponivel()).build();
    }
}