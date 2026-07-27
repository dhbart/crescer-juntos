package dhbart.crescerjuntos.application.dto.familia;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaCreateDTO {
    
    @NotBlank(message = "Nome da família é obrigatório")
    private String nome;
}