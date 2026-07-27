package dhbart.crescerjuntos.criancafamilia.domain.repository;

import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;

import java.util.List;
import java.util.Optional;

public interface CriancaRepository {
    Crianca salvar(Crianca crianca);
    Optional<Crianca> buscarPorId(Long id);
    List<Crianca> buscarPorFamilia(Long familiaId);
    List<Crianca> buscarAtivasPorFamilia(Long familiaId);
    boolean existe(Long id);
    void excluir(Long id);
}