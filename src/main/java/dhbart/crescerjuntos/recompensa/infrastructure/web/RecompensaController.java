package dhbart.crescerjuntos.recompensa.infrastructure.web;

import dhbart.crescerjuntos.recompensa.application.*;
import dhbart.crescerjuntos.recompensa.application.dto.RecompensaRequest;
import dhbart.crescerjuntos.recompensa.application.dto.RecompensaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
@RequiredArgsConstructor
@Tag(name = "Recompensas", description = "Gerenciamento de recompensas")
public class RecompensaController {

    private final CriarRecompensaUseCase criarUseCase;
    private final RecompensaQueryService queryService;

    @PostMapping
    @Operation(summary = "Criar nova recompensa")
    public ResponseEntity<RecompensaResponse> criar(@Valid @RequestBody RecompensaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar recompensa por ID")
    public ResponseEntity<RecompensaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(queryService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar recompensas por familia")
    public ResponseEntity<List<RecompensaResponse>> listar(@RequestParam Long familiaId) {
        return ResponseEntity.ok(queryService.listarPorFamilia(familiaId));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar recompensas disponiveis por familia")
    public ResponseEntity<List<RecompensaResponse>> listarDisponiveis(@RequestParam Long familiaId) {
        return ResponseEntity.ok(queryService.listarDisponiveis(familiaId));
    }
}