package dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.FamiliaEntity;
import org.springframework.stereotype.Component;

@Component
public class CriancaEntityMapper {

    public CriancaEntity toEntity(Crianca domain, FamiliaEntity familia) {
        if (domain == null) return null;
        return CriancaEntity.builder()
                .id(domain.getId())
                .apelido(domain.getApelido())
                .dataNascimento(domain.getDataNascimento())
                .familia(familia)
                .ativa(domain.isAtiva())
                .build();
    }

    public Crianca toDomain(CriancaEntity entity) {
        if (entity == null) return null;
        return Crianca.builder()
                .id(entity.getId())
                .apelido(entity.getApelido())
                .dataNascimento(entity.getDataNascimento())
                .familiaId(entity.getFamilia().getId())
                .ativa(entity.isAtiva())
                .build();
    }
}