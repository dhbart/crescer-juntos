package dhbart.crescerjuntos.domain.repository;  
  
import dhbart.crescerjuntos.domain.model.Resgate;  
import dhbart.crescerjuntos.domain.model.StatusResgate;  
import java.util.List;  
import java.util.Optional;  
  
public interface ResgateRepository {  
    Resgate salvar(Resgate resgate);  
    Optional<Resgate> buscarPorId(Long id);  
    List<Resgate> buscarPorCrianca(Long criancaId);  
    List<Resgate> buscarPorCriancaEStatus(Long criancaId, StatusResgate status);  
    List<Resgate> buscarPorFamilia(Long familiaId);  
    boolean existe(Long id);  
    void excluir(Long id);  
}  
