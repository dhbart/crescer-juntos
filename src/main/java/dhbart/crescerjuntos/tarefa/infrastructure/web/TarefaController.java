package dhbart.crescerjuntos.tarefa.infrastructure.web;

import dhbart.crescerjuntos.tarefa.application.*;
import dhbart.crescerjuntos.tarefa.application.dto.TarefaRequest;
import dhbart.crescerjuntos.tarefa.application.dto.TarefaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas")
public class TarefaController {

    private final CriarTarefaUseCase criarTarefaUseCase;
    private final BuscarTarefaUseCase buscarTarefaUseCase;
    private final ListarTarefasUseCase listarTarefasUseCase;

    @PostMapping
    @Operation(summary = "Criar nova tarefa")
    public ResponseEntity<TarefaResponse> criar(@Valid @RequestBody TarefaRequest request) {
        TarefaResponse response = criarTarefaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID")
    public ResponseEntity<TarefaResponse> buscarPorId(@PathVariable Long id) {
        TarefaResponse response = buscarTarefaUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar tarefas da familia")
    public ResponseEntity<List<TarefaResponse>> listar(
            @RequestParam Long familiaId,
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {
        List<TarefaResponse> response = listarTarefasUseCase.executePorFamilia(familiaId, apenasAtivas);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativas")
    @Operation(summary = "Listar tarefas ativas na data")
    public ResponseEntity<List<TarefaResponse>> listarAtivasNaData(
            @RequestParam Long familiaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<TarefaResponse> response = listarTarefasUseCase.executeAtivasNaData(familiaId, data);
        return ResponseEntity.ok(response);
    }
}