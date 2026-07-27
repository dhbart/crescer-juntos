package dhbart.crescerjuntos.application.dto.recompensa;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class RecompensaCreateDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @Min(value = 1, message = "Custo mínimo: 1 ponto")
    @Max(value = 1000, message = "Custo máximo: 1000 pontos")
    private int custoPontos;

    @NotNull(message = "ID da família é obrigatório")
    private Long familiaId;
}
