package dhbart.crescerjuntos.application.dto.familia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaResponseDTO {

    private Long id;
    private String nome;
    private boolean ativa;
}
