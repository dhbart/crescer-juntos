package dhbart.crescerjuntos.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.domain.model.Familia;
import dhbart.crescerjuntos.infrastructure.persistence.entity.FamiliaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class FamiliaEntityMapper {

    public FamiliaEntity toEntity(Familia domain) {
        if (domain == null) {
            return null;
        }
        
        return FamiliaEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .ativa(domain.isAtiva())
                .criancas(new ArrayList<>()) // Relacionamentos gerenciados separadamente
                .build();
    }

    public Familia toDomain(FamiliaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Familia.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .ativa(entity.isAtiva())
                .criancas(new ArrayList<>()) // Relacionamentos carregados sob demanda
                .build();
    }
}