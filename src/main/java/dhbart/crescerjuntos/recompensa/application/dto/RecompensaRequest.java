package dhbart.crescerjuntos.recompensa.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecompensaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private String descricao;
    private int custoPontos;
    @NotNull(message = "ID da família é obrigatório")
    private Long familiaId;
}