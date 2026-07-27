package dhbart.crescerjuntos.criancafamilia.application.dto;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CriancaResponse {
    private Long id;
    private String apelido;
    private LocalDate dataNascimento;
    private int idade;
    private Long familiaId;
    private boolean ativa;

    public static CriancaResponse from(Crianca crianca) {
        return CriancaResponse.builder()
                .id(crianca.getId())
                .apelido(crianca.getApelido())
                .dataNascimento(crianca.getDataNascimento())
                .idade(crianca.getIdade())
                .familiaId(crianca.getFamiliaId())
                .ativa(crianca.isAtiva())
                .build();
    }
}