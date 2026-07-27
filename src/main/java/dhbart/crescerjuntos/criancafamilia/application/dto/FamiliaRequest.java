package dhbart.crescerjuntos.criancafamilia.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FamiliaRequest {
    @NotBlank(message = "Nome da família é obrigatório")
    private String nome;
}