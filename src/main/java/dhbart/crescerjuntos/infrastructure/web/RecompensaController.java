package dhbart.crescerjuntos.infrastructure.web;

import dhbart.crescerjuntos.application.dto.mapper.RecompensaMapper;
import dhbart.crescerjuntos.application.dto.recompensa.RecompensaCreateDTO;
import dhbart.crescerjuntos.application.dto.recompensa.RecompensaResponseDTO;
import dhbart.crescerjuntos.application.dto.recompensa.RecompensaUpdateDTO;
import dhbart.crescerjuntos.domain.model.Recompensa;
import dhbart.crescerjuntos.domain.service.RecompensaService;
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

    private final RecompensaService recompensaService;
    private final RecompensaMapper recompensaMapper;

    @PostMapping
    @Operation(summary = "Criar nova recompensa")
    public ResponseEntity<RecompensaResponseDTO> criar(@Valid @RequestBody RecompensaCreateDTO dto) {
        Recompensa recompensa = recompensaMapper.toDomain(dto);
        Recompensa criada = recompensaService.criar(recompensa);
        return ResponseEntity.status(HttpStatus.CREATED).body(recompensaMapper.toResponse(criada));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar recompensa por ID")
    public ResponseEntity<RecompensaResponseDTO> buscarPorId(@PathVariable Long id) {
        Recompensa recompensa = recompensaService.buscarPorId(id);
        return ResponseEntity.ok(recompensaMapper.toResponse(recompensa));
    }

    @GetMapping
    @Operation(summary = "Listar recompensas por familia")
    public ResponseEntity<List<RecompensaResponseDTO>> listar(@RequestParam Long familiaId) {
        List<Recompensa> recompensas = recompensaService.listarPorFamilia(familiaId);
        List<RecompensaResponseDTO> response = recompensas.stream().map(recompensaMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar recompensas disponiveis por familia")
    public ResponseEntity<List<RecompensaResponseDTO>> listarDisponiveis(@RequestParam Long familiaId) {
        List<Recompensa> recompensas = recompensaService.listarDisponiveisPorFamilia(familiaId);
        List<RecompensaResponseDTO> response = recompensas.stream().map(recompensaMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar recompensa")
    public ResponseEntity<RecompensaResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody RecompensaUpdateDTO dto) {
        Recompensa atualizada = recompensaService.atualizar(id, dto.getNome(), dto.getDescricao(), dto.getCustoPontos());
        return ResponseEntity.ok(recompensaMapper.toResponse(atualizada));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar recompensa")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        recompensaService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar recompensa")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        recompensaService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir recompensa")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        recompensaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
