package dhbart.crescerjuntos.shared.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException familia(Long id) {
        return new ResourceNotFoundException("Família não encontrada com id: " + id);
    }

    public static ResourceNotFoundException crianca(Long id) {
        return new ResourceNotFoundException("Criança não encontrada com id: " + id);
    }

    public static ResourceNotFoundException tarefa(Long id) {
        return new ResourceNotFoundException("Tarefa não encontrada com id: " + id);
    }

    public static ResourceNotFoundException execucaoTarefa(Long id) {
        return new ResourceNotFoundException("Execução de tarefa não encontrada com id: " + id);
    }

    public static ResourceNotFoundException recompensa(Long id) {
        return new ResourceNotFoundException("Recompensa não encontrada com id: " + id);
    }

    public static ResourceNotFoundException resgate(Long id) {
        return new ResourceNotFoundException("Resgate não encontrado com id: " + id);
    }
}