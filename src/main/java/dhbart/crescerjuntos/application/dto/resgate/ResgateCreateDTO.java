package dhbart.crescerjuntos.application.dto.resgate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResgateCreateDTO {

    @NotNull(message = "ID da criança é obrigatório")
    private Long criancaId;

    @NotNull(message = "ID da recompensa é obrigatório")
    private Long recompensaId;

    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    private String observacao;
}
