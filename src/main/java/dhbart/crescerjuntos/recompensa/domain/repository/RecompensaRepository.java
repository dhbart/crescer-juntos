package dhbart.crescerjuntos.recompensa.domain.repository;

import dhbart.crescerjuntos.recompensa.domain.model.Recompensa;
import java.util.List;
import java.util.Optional;

public interface RecompensaRepository {
    Recompensa salvar(Recompensa recompensa);
    Optional<Recompensa> buscarPorId(Long id);
    List<Recompensa> buscarPorFamilia(Long familiaId);
    List<Recompensa> buscarDisponiveisPorFamilia(Long familiaId);
    boolean existe(Long id);
    void excluir(Long id);
}