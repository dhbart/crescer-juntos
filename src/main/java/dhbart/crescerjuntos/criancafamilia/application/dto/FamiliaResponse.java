package dhbart.crescerjuntos.criancafamilia.application.dto;

import dhbart.crescerjuntos.criancafamilia.domain.model.Familia;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamiliaResponse {
    private Long id;
    private String nome;
    private boolean ativa;

    public static FamiliaResponse from(Familia familia) {
        return FamiliaResponse.builder()
                .id(familia.getId())
                .nome(familia.getNome())
                .ativa(familia.isAtiva())
                .build();
    }
}