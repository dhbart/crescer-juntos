package dhbart.crescerjuntos.recompensa.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.FamiliaEntity;
import dhbart.crescerjuntos.recompensa.domain.model.Recompensa;
import dhbart.crescerjuntos.recompensa.infrastructure.persistence.entity.RecompensaEntity;
import org.springframework.stereotype.Component;

@Component
public class RecompensaEntityMapper {

    public RecompensaEntity toEntity(Recompensa domain, FamiliaEntity familia) {
        if (domain == null) return null;
        return RecompensaEntity.builder()
                .id(domain.getId()).nome(domain.getNome())
                .descricao(domain.getDescricao()).custoPontos(domain.getCustoPontos())
                .familia(familia).disponivel(domain.isDisponivel()).build();
    }

    public Recompensa toDomain(RecompensaEntity entity) {
        if (entity == null) return null;
        return Recompensa.builder()
                .id(entity.getId()).nome(entity.getNome())
                .descricao(entity.getDescricao()).custoPontos(entity.getCustoPontos())
                .familiaId(entity.getFamilia().getId()).disponivel(entity.isDisponivel()).build();
    }
}