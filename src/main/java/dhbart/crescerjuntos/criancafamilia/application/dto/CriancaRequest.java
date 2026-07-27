package dhbart.crescerjuntos.criancafamilia.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CriancaRequest {
    @NotBlank(message = "Apelido é obrigatório")
    private String apelido;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "ID da família é obrigatório")
    private Long familiaId;
}