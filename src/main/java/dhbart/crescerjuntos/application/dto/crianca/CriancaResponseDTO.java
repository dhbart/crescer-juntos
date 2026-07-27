package dhbart.crescerjuntos.application.dto.crianca;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriancaResponseDTO {

    private Long id;
    private String apelido;
    private LocalDate dataNascimento;
    private Long familiaId;
    private boolean ativa;
}
