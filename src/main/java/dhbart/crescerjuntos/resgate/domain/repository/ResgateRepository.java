package dhbart.crescerjuntos.resgate.domain.repository;

import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import dhbart.crescerjuntos.resgate.domain.model.Resgate;
import java.util.List;
import java.util.Optional;

public interface ResgateRepository {
    Resgate salvar(Resgate resgate);
    Optional<Resgate> buscarPorId(Long id);
    List<Resgate> buscarPorCrianca(Long criancaId);
    List<Resgate> buscarPorCriancaEStatus(Long criancaId, StatusResgate status);
    boolean existe(Long id);
    void excluir(Long id);
}