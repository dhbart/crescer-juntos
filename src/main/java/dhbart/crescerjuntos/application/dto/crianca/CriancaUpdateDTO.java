package dhbart.crescerjuntos.application.dto.crianca;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriancaUpdateDTO {
    
    @NotBlank(message = "Apelido é obrigatório")
    private String apelido;
}