package dhbart.crescerjuntos.execucaotarefa.infrastructure.web;

import dhbart.crescerjuntos.execucaotarefa.application.*;
import dhbart.crescerjuntos.execucaotarefa.application.dto.ExecucaoTarefaRequest;
import dhbart.crescerjuntos.execucaotarefa.application.dto.ExecucaoTarefaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/execucoes-tarefas")
@RequiredArgsConstructor
@Tag(name = "Execucoes de Tarefas", description = "Gerenciamento de execucoes de tarefas")
public class ExecucaoTarefaController {

    private final RegistrarExecucaoTarefaUseCase registrarUseCase;
    private final BuscarExecucaoTarefaUseCase buscarUseCase;
    private final AprovarExecucaoTarefaUseCase aprovarUseCase;
    private final RejeitarExecucaoTarefaUseCase rejeitarUseCase;
    private final ExpirarExecucaoTarefaUseCase expirarUseCase;

    @PostMapping
    @Operation(summary = "Registrar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponse> registrar(@Valid @RequestBody ExecucaoTarefaRequest request) {
        ExecucaoTarefaResponse response = registrarUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar execucao por ID")
    public ResponseEntity<ExecucaoTarefaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(buscarUseCase.execute(id));
    }

    @GetMapping("/crianca/{criancaId}")
    @Operation(summary = "Listar execucoes por crianca")
    public ResponseEntity<List<ExecucaoTarefaResponse>> listarPorCrianca(@PathVariable Long criancaId) {
        return ResponseEntity.ok(buscarUseCase.executePorCrianca(criancaId));
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponse> aprovar(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(aprovarUseCase.execute(id, body.getOrDefault("pontos", 0)));
    }

    @PatchMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponse> rejeitar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(rejeitarUseCase.execute(id, body.getOrDefault("motivo", "")));
    }

    @PatchMapping("/{id}/expirar")
    @Operation(summary = "Expirar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponse> expirar(@PathVariable Long id) {
        return ResponseEntity.ok(expirarUseCase.execute(id));
    }
}