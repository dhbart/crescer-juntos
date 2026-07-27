package dhbart.crescerjuntos.infrastructure.web;

import dhbart.crescerjuntos.application.dto.mapper.ResgateMapper;
import dhbart.crescerjuntos.application.dto.resgate.RejeitarResgateDTO;
import dhbart.crescerjuntos.application.dto.resgate.ResgateCreateDTO;
import dhbart.crescerjuntos.application.dto.resgate.ResgateResponseDTO;
import dhbart.crescerjuntos.domain.model.Resgate;
import dhbart.crescerjuntos.domain.model.StatusResgate;
import dhbart.crescerjuntos.domain.service.RecompensaService;
import dhbart.crescerjuntos.domain.service.ResgateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resgates")
@RequiredArgsConstructor
@Tag(name = "Resgates", description = "Gerenciamento de resgates")
public class ResgateController {

    private final ResgateService resgateService;
    private final RecompensaService recompensaService;
    private final ResgateMapper resgateMapper;

    @PostMapping
    @Operation(summary = "Solicitar resgate")
    public ResponseEntity<ResgateResponseDTO> solicitar(@Valid @RequestBody ResgateCreateDTO dto) {
        int pontosUtilizados = recompensaService.buscarPorId(dto.getRecompensaId()).getCustoPontos();
        Resgate resgate = resgateMapper.toDomain(dto, pontosUtilizados);
        Resgate solicitado = resgateService.solicitar(resgate);
        return ResponseEntity.status(HttpStatus.CREATED).body(resgateMapper.toResponse(solicitado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar resgate por ID")
    public ResponseEntity<ResgateResponseDTO> buscarPorId(@PathVariable Long id) {
        Resgate resgate = resgateService.buscarPorId(id);
        return ResponseEntity.ok(resgateMapper.toResponse(resgate));
    }

    @GetMapping("/crianca/{criancaId}")
    @Operation(summary = "Listar resgates por crianca")
    public ResponseEntity<List<ResgateResponseDTO>> listarPorCrianca(@PathVariable Long criancaId) {
        List<Resgate> resgates = resgateService.listarPorCrianca(criancaId);
        List<ResgateResponseDTO> response = resgates.stream().map(resgateMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/crianca/{criancaId}/status/{status}")
    @Operation(summary = "Listar resgates por crianca e status")
    public ResponseEntity<List<ResgateResponseDTO>> listarPorCriancaEStatus(
            @PathVariable Long criancaId, @PathVariable StatusResgate status) {
        List<Resgate> resgates = resgateService.listarPorCriancaEStatus(criancaId, status);
        List<ResgateResponseDTO> response = resgates.stream().map(resgateMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar resgate")
    public ResponseEntity<ResgateResponseDTO> aprovar(@PathVariable Long id) {
        Resgate resgate = resgateService.aprovar(id);
        return ResponseEntity.ok(resgateMapper.toResponse(resgate));
    }

    @PatchMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar resgate")
    public ResponseEntity<ResgateResponseDTO> rejeitar(
            @PathVariable Long id, @Valid @RequestBody RejeitarResgateDTO dto) {
        Resgate resgate = resgateService.rejeitar(id, dto.getMotivo());
        return ResponseEntity.ok(resgateMapper.toResponse(resgate));
    }

    @PatchMapping("/{id}/entregar")
    @Operation(summary = "Entregar resgate")
    public ResponseEntity<ResgateResponseDTO> entregar(@PathVariable Long id) {
        Resgate resgate = resgateService.entregar(id);
        return ResponseEntity.ok(resgateMapper.toResponse(resgate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir resgate")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        resgateService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
