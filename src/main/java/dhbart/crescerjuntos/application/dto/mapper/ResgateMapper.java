package dhbart.crescerjuntos.application.dto.mapper;

import dhbart.crescerjuntos.application.dto.resgate.ResgateCreateDTO;
import dhbart.crescerjuntos.application.dto.resgate.ResgateResponseDTO;
import dhbart.crescerjuntos.domain.model.Resgate;
import org.springframework.stereotype.Component;

@Component
public class ResgateMapper {

    public Resgate toDomain(ResgateCreateDTO dto, int pontosUtilizados) {
        return Resgate.builder()
                .criancaId(dto.getCriancaId())
                .recompensaId(dto.getRecompensaId())
                .pontosUtilizados(pontosUtilizados)
                .observacao(dto.getObservacao())
                .build();
    }

    public ResgateResponseDTO toResponse(Resgate domain) {
        return ResgateResponseDTO.builder()
                .id(domain.getId())
                .criancaId(domain.getCriancaId())
                .recompensaId(domain.getRecompensaId())
                .status(domain.getStatus())
                .pontosUtilizados(domain.getPontosUtilizados())
                .observacao(domain.getObservacao())
                .build();
    }
}
