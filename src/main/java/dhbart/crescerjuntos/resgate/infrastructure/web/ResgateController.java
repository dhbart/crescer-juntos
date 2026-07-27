package dhbart.crescerjuntos.resgate.infrastructure.web;

import dhbart.crescerjuntos.resgate.domain.model.StatusResgate;
import dhbart.crescerjuntos.resgate.application.*;
import dhbart.crescerjuntos.resgate.application.dto.ResgateRequest;
import dhbart.crescerjuntos.resgate.application.dto.ResgateResponse;
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
@RequestMapping("/api/resgates")
@RequiredArgsConstructor
@Tag(name = "Resgates", description = "Gerenciamento de resgates")
public class ResgateController {

    private final SolicitarResgateUseCase solicitarUseCase;
    private final ResgateQueryService queryService;
    private final AprovarResgateUseCase aprovarUseCase;
    private final RejeitarResgateUseCase rejeitarUseCase;
    private final MarcarEntregaResgateUseCase entregarUseCase;

    @PostMapping
    @Operation(summary = "Solicitar resgate")
    public ResponseEntity<ResgateResponse> solicitar(@Valid @RequestBody ResgateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitarUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar resgate por ID")
    public ResponseEntity<ResgateResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(queryService.buscarPorId(id));
    }

    @GetMapping("/crianca/{criancaId}")
    @Operation(summary = "Listar resgates por crianca")
    public ResponseEntity<List<ResgateResponse>> listarPorCrianca(@PathVariable Long criancaId) {
        return ResponseEntity.ok(queryService.listarPorCrianca(criancaId));
    }

    @GetMapping("/crianca/{criancaId}/status/{status}")
    @Operation(summary = "Listar resgates por crianca e status")
    public ResponseEntity<List<ResgateResponse>> listarPorCriancaEStatus(
            @PathVariable Long criancaId, @PathVariable StatusResgate status) {
        return ResponseEntity.ok(queryService.listarPorCriancaEStatus(criancaId, status));
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar resgate")
    public ResponseEntity<ResgateResponse> aprovar(@PathVariable Long id) {
        return ResponseEntity.ok(aprovarUseCase.execute(id));
    }

    @PatchMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar resgate")
    public ResponseEntity<ResgateResponse> rejeitar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(rejeitarUseCase.execute(id, body.getOrDefault("motivo", "")));
    }

    @PatchMapping("/{id}/entregar")
    @Operation(summary = "Entregar resgate")
    public ResponseEntity<ResgateResponse> entregar(@PathVariable Long id) {
        return ResponseEntity.ok(entregarUseCase.execute(id));
    }
}