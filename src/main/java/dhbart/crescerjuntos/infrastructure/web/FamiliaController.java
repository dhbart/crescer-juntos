package dhbart.crescerjuntos.infrastructure.web;

import dhbart.crescerjuntos.application.dto.familia.FamiliaCreateDTO;
import dhbart.crescerjuntos.application.dto.familia.FamiliaResponseDTO;
import dhbart.crescerjuntos.application.dto.familia.FamiliaUpdateDTO;
import dhbart.crescerjuntos.application.dto.mapper.FamiliaMapper;
import dhbart.crescerjuntos.domain.model.Familia;
import dhbart.crescerjuntos.domain.service.FamiliaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/familias")
@RequiredArgsConstructor
@Tag(name = "Famílias", description = "Gerenciamento de famílias")
public class FamiliaController {
    
    private final FamiliaService familiaService;
    private final FamiliaMapper familiaMapper;
    
    @PostMapping
    @Operation(summary = "Criar nova família")
    public ResponseEntity<FamiliaResponseDTO> criar(@Valid @RequestBody FamiliaCreateDTO dto) {
        Familia familia = familiaMapper.toDomain(dto);
        Familia criada = familiaService.criar(familia);
        return ResponseEntity.status(HttpStatus.CREATED).body(familiaMapper.toResponse(criada));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar família por ID")
    public ResponseEntity<FamiliaResponseDTO> buscarPorId(@PathVariable Long id) {
        Familia familia = familiaService.buscarPorId(id);
        return ResponseEntity.ok(familiaMapper.toResponse(familia));
    }
    
    @GetMapping
    @Operation(summary = "Listar todas as famílias")
    public ResponseEntity<List<FamiliaResponseDTO>> listarTodas(
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {
        List<Familia> familias = apenasAtivas ? 
                familiaService.listarAtivas() : familiaService.listarTodas();
        List<FamiliaResponseDTO> response = familias.stream()
                .map(familiaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar família")
    public ResponseEntity<FamiliaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FamiliaUpdateDTO dto) {
        Familia atualizada = familiaService.atualizar(id, dto.getNome());
        return ResponseEntity.ok(familiaMapper.toResponse(atualizada));
    }
    
    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar família")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        familiaService.ativar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar família")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        familiaService.desativar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir família")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        familiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}