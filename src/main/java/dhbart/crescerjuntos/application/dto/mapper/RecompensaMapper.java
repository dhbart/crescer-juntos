package dhbart.crescerjuntos.application.dto.mapper;

import dhbart.crescerjuntos.application.dto.recompensa.RecompensaCreateDTO;
import dhbart.crescerjuntos.application.dto.recompensa.RecompensaResponseDTO;
import dhbart.crescerjuntos.domain.model.Recompensa;
import org.springframework.stereotype.Component;

@Component
public class RecompensaMapper {

    public Recompensa toDomain(RecompensaCreateDTO dto) {
        return Recompensa.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .custoPontos(dto.getCustoPontos())
                .familiaId(dto.getFamiliaId())
                .build();
    }

    public RecompensaResponseDTO toResponse(Recompensa domain) {
        return RecompensaResponseDTO.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .custoPontos(domain.getCustoPontos())
                .familiaId(domain.getFamiliaId())
                .disponivel(domain.isDisponivel())
                .build();
    }
}
