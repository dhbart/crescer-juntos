package dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.criancafamilia.domain.model.Familia;
import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.FamiliaEntity;
import org.springframework.stereotype.Component;

@Component
public class FamiliaEntityMapper {

    public FamiliaEntity toEntity(Familia domain) {
        if (domain == null) return null;
        return FamiliaEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .ativa(domain.isAtiva())
                .build();
    }

    public Familia toDomain(FamiliaEntity entity) {
        if (entity == null) return null;
        return Familia.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .ativa(entity.isAtiva())
                .build();
    }
}