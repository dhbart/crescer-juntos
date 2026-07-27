package dhbart.crescerjuntos.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.domain.model.Crianca;
import dhbart.crescerjuntos.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriancaEntityMapper {

    public CriancaEntity toEntity(Crianca domain, FamiliaEntity familiaEntity) {
        if (domain == null) {
            return null;
        }
        
        return CriancaEntity.builder()
                .id(domain.getId())
                .apelido(domain.getApelido())
                .dataNascimento(domain.getDataNascimento())
                .familia(familiaEntity)
                .ativa(domain.isAtiva())
                .build();
    }

    public Crianca toDomain(CriancaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Crianca.builder()
                .id(entity.getId())
                .apelido(entity.getApelido())
                .dataNascimento(entity.getDataNascimento())
                .familiaId(entity.getFamilia() != null ? entity.getFamilia().getId() : null)
                .ativa(entity.isAtiva())
                .build();
    }
}