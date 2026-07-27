package dhbart.crescerjuntos.resgate.infrastructure.persistence.mapper;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.CriancaEntity;
import dhbart.crescerjuntos.recompensa.infrastructure.persistence.entity.RecompensaEntity;
import dhbart.crescerjuntos.resgate.domain.model.Resgate;
import dhbart.crescerjuntos.resgate.infrastructure.persistence.entity.ResgateEntity;
import org.springframework.stereotype.Component;

@Component
public class ResgateEntityMapper {

    public ResgateEntity toEntity(Resgate domain, CriancaEntity crianca, RecompensaEntity recompensa) {
        if (domain == null) return null;
        return ResgateEntity.builder()
                .id(domain.getId()).crianca(crianca).recompensa(recompensa)
                .status(domain.getStatus()).pontosUtilizados(domain.getPontosUtilizados())
                .observacao(domain.getObservacao()).build();
    }

    public Resgate toDomain(ResgateEntity entity) {
        if (entity == null) return null;
        return Resgate.builder()
                .id(entity.getId()).criancaId(entity.getCrianca().getId())
                .recompensaId(entity.getRecompensa().getId())
                .status(entity.getStatus()).pontosUtilizados(entity.getPontosUtilizados())
                .observacao(entity.getObservacao()).build();
    }
}