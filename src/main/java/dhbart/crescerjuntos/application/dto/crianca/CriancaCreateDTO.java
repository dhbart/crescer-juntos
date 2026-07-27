package dhbart.crescerjuntos.application.dto.crianca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriancaCreateDTO {

    @NotBlank(message = "Apelido e obrigatorio")
    private String apelido;

    @NotNull(message = "Data de nascimento e obrigatoria")
    private LocalDate dataNascimento;

    @NotNull(message = "ID da familia e obrigatorio")
    private Long familiaId;
}
