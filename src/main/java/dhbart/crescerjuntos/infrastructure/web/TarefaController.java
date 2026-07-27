package dhbart.crescerjuntos.infrastructure.web;

import dhbart.crescerjuntos.application.dto.tarefa.TarefaCreateDTO;
import dhbart.crescerjuntos.application.dto.tarefa.TarefaResponseDTO;
import dhbart.crescerjuntos.application.dto.tarefa.TarefaUpdateDTO;
import dhbart.crescerjuntos.application.dto.mapper.TarefaMapper;
import dhbart.crescerjuntos.domain.model.Tarefa;
import dhbart.crescerjuntos.domain.service.TarefaService;
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

    private final TarefaService tarefaService;
    private final TarefaMapper tarefaMapper;

    @PostMapping
    @Operation(summary = "Criar nova tarefa")
    public ResponseEntity<TarefaResponseDTO> criar(@Valid @RequestBody TarefaCreateDTO dto) {
        Tarefa tarefa = tarefaMapper.toDomain(dto);
        Tarefa criada = tarefaService.criar(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaMapper.toResponse(criada));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(tarefaMapper.toResponse(tarefa));
    }

    @GetMapping
    @Operation(summary = "Listar tarefas da familia")
    public ResponseEntity<List<TarefaResponseDTO>> listar(
            @RequestParam Long familiaId,
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {
        List<Tarefa> tarefas = apenasAtivas ?
                tarefaService.listarAtivasPorFamilia(familiaId) :
                tarefaService.listarPorFamilia(familiaId);
        List<TarefaResponseDTO> response = tarefas.stream()
                .map(tarefaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativas")
    @Operation(summary = "Listar tarefas ativas na data")
    public ResponseEntity<List<TarefaResponseDTO>> listarAtivasNaData(
            @RequestParam Long familiaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Tarefa> tarefas = tarefaService.listarAtivasNaData(familiaId, data);
        List<TarefaResponseDTO> response = tarefas.stream()
                .map(tarefaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar tarefa")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        tarefaService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar tarefa")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        tarefaService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tarefa")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        tarefaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
