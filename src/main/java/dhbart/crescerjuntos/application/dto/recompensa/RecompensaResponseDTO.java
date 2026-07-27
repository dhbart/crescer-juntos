package dhbart.crescerjuntos.application.dto.recompensa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecompensaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private int custoPontos;
    private Long familiaId;
    private boolean disponivel;
}
