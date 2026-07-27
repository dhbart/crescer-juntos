package dhbart.crescerjuntos.infrastructure.web;

import dhbart.crescerjuntos.application.dto.crianca.CriancaCreateDTO;
import dhbart.crescerjuntos.application.dto.crianca.CriancaResponseDTO;
import dhbart.crescerjuntos.application.dto.mapper.CriancaMapper;
import dhbart.crescerjuntos.domain.model.Crianca;
import dhbart.crescerjuntos.domain.service.CriancaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criancas")
@RequiredArgsConstructor
@Tag(name = "Criancas", description = "Gerenciamento de criancas")
public class CriancaController {

    private final CriancaService criancaService;
    private final CriancaMapper criancaMapper;

    @PostMapping
    @Operation(summary = "Criar nova crianca")
    public ResponseEntity<CriancaResponseDTO> criar(@Valid @RequestBody CriancaCreateDTO dto) {
        Crianca crianca = criancaMapper.toDomain(dto);
        Crianca criada = criancaService.criar(crianca);
        return ResponseEntity.status(HttpStatus.CREATED).body(criancaMapper.toResponse(criada));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar crianca por ID")
    public ResponseEntity<CriancaResponseDTO> buscarPorId(@PathVariable Long id) {
        Crianca crianca = criancaService.buscarPorId(id);
        return ResponseEntity.ok(criancaMapper.toResponse(crianca));
    }

    @GetMapping
    @Operation(summary = "Listar criancas")
    public ResponseEntity<List<CriancaResponseDTO>> listar(
            @RequestParam(required = false) Long familiaId,
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {
        List<Crianca> criancas;
        if (familiaId != null) {
            criancas = apenasAtivas ?
                    criancaService.listarAtivasPorFamilia(familiaId) :
                    criancaService.listarPorFamilia(familiaId);
        } else {
            criancas = criancaService.listarTodas();
        }
        List<CriancaResponseDTO> response = criancas.stream()
                .map(criancaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar apelido da crianca")
    public ResponseEntity<CriancaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestParam String novoApelido) {
        Crianca atualizada = criancaService.atualizarApelido(id, novoApelido);
        return ResponseEntity.ok(criancaMapper.toResponse(atualizada));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar crianca")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        criancaService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar crianca")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        criancaService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir crianca")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        criancaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
