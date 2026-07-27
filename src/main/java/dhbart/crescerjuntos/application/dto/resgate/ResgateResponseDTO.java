package dhbart.crescerjuntos.application.dto.resgate;

import dhbart.crescerjuntos.domain.model.StatusResgate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResgateResponseDTO {

    private Long id;
    private Long criancaId;
    private Long recompensaId;
    private StatusResgate status;
    private int pontosUtilizados;
    private String observacao;
}
