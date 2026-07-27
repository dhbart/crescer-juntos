package dhbart.crescerjuntos.application.dto.mapper;

import dhbart.crescerjuntos.application.dto.crianca.CriancaCreateDTO;
import dhbart.crescerjuntos.application.dto.crianca.CriancaResponseDTO;
import dhbart.crescerjuntos.domain.model.Crianca;
import org.springframework.stereotype.Component;

@Component
public class CriancaMapper {

    public Crianca toDomain(CriancaCreateDTO dto) {
        return Crianca.builder()
                .apelido(dto.getApelido())
                .dataNascimento(dto.getDataNascimento())
                .familiaId(dto.getFamiliaId())
                .build();
    }

    public CriancaResponseDTO toResponse(Crianca crianca) {
        return CriancaResponseDTO.builder()
                .id(crianca.getId())
                .apelido(crianca.getApelido())
                .dataNascimento(crianca.getDataNascimento())
                .familiaId(crianca.getFamiliaId())
                .ativa(crianca.isAtiva())
                .build();
    }
}
