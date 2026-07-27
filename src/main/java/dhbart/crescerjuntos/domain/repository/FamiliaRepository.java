package dhbart.crescerjuntos.domain.repository;  
  
import dhbart.crescerjuntos.domain.model.Familia;  
import java.util.List;  
import java.util.Optional;  
  
public interface FamiliaRepository {  
    Familia salvar(Familia familia);  
    Optional<Familia> buscarPorId(Long id);  
    List<Familia> buscarTodas();  
    List<Familia> buscarAtivas();  
    boolean existe(Long id);  
    void excluir(Long id);  
}  
