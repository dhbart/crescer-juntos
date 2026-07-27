package dhbart.crescerjuntos.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.domain.model.Recompensa;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.RecompensaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecompensaEntityMapper {

    public RecompensaEntity toEntity(Recompensa domain, FamiliaEntity familiaEntity) {
        if (domain == null) {
            return null;
        }
        
        return RecompensaEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .custoPontos(domain.getCustoPontos())
                .familia(familiaEntity)
                .disponivel(domain.isDisponivel())
                .build();
    }

    public Recompensa toDomain(RecompensaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Recompensa.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .custoPontos(entity.getCustoPontos())
                .familiaId(entity.getFamilia() != null ? entity.getFamilia().getId() : null)
                .disponivel(entity.isDisponivel())
                .build();
    }
}