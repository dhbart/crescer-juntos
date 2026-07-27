package dhbart.crescerjuntos.resgate.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResgateRequest {
    @NotNull private Long criancaId;
    @NotNull private Long recompensaId;
}