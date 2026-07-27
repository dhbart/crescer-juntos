package dhbart.crescerjuntos.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.domain.model.Resgate;
import dhbart.crescerjuntos.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.RecompensaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.ResgateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResgateEntityMapper {

    public ResgateEntity toEntity(Resgate domain, CriancaEntity criancaEntity, RecompensaEntity recompensaEntity) {
        if (domain == null) {
            return null;
        }
        
        return ResgateEntity.builder()
                .id(domain.getId())
                .crianca(criancaEntity)
                .recompensa(recompensaEntity)
                .status(domain.getStatus())
                .pontosUtilizados(domain.getPontosUtilizados())
                .observacao(domain.getObservacao())
                .build();
    }

    public Resgate toDomain(ResgateEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Resgate.builder()
                .id(entity.getId())
                .criancaId(entity.getCrianca() != null ? entity.getCrianca().getId() : null)
                .recompensaId(entity.getRecompensa() != null ? entity.getRecompensa().getId() : null)
                .status(entity.getStatus())
                .pontosUtilizados(entity.getPontosUtilizados())
                .observacao(entity.getObservacao())
                .build();
    }
}