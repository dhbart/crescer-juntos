package dhbart.crescerjuntos.application.dto.mapper;

import dhbart.crescerjuntos.application.dto.familia.FamiliaCreateDTO;
import dhbart.crescerjuntos.application.dto.familia.FamiliaResponseDTO;
import dhbart.crescerjuntos.domain.model.Familia;
import org.springframework.stereotype.Component;

@Component
public class FamiliaMapper {
    
    public Familia toDomain(FamiliaCreateDTO dto) {
        return Familia.builder()
                .nome(dto.getNome())
                .ativa(true)
                .build();
    }
    
    public FamiliaResponseDTO toResponse(Familia familia) {
        return FamiliaResponseDTO.builder()
                .id(familia.getId())
                .nome(familia.getNome())
                .ativa(familia.isAtiva())
                .build();
    }
}