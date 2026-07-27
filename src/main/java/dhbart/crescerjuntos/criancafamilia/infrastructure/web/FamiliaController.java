package dhbart.crescerjuntos.criancafamilia.infrastructure.web;

import dhbart.crescerjuntos.criancafamilia.application.*;
import dhbart.crescerjuntos.criancafamilia.application.dto.FamiliaRequest;
import dhbart.crescerjuntos.criancafamilia.application.dto.FamiliaResponse;
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

    private final CriarFamiliaUseCase criarFamiliaUseCase;
    private final BuscarFamiliaUseCase buscarFamiliaUseCase;
    private final ListarFamiliasUseCase listarFamiliasUseCase;
    private final AtualizarFamiliaUseCase atualizarFamiliaUseCase;
    private final AtivarFamiliaUseCase ativarFamiliaUseCase;
    private final DesativarFamiliaUseCase desativarFamiliaUseCase;

    @PostMapping
    @Operation(summary = "Criar nova família")
    public ResponseEntity<FamiliaResponse> criar(@Valid @RequestBody FamiliaRequest request) {
        FamiliaResponse response = criarFamiliaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar família por ID")
    public ResponseEntity<FamiliaResponse> buscarPorId(@PathVariable Long id) {
        FamiliaResponse response = buscarFamiliaUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as famílias")
    public ResponseEntity<List<FamiliaResponse>> listarTodas(
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {
        List<FamiliaResponse> response = listarFamiliasUseCase.execute(apenasAtivas);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar família")
    public ResponseEntity<FamiliaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FamiliaRequest request) {
        FamiliaResponse response = atualizarFamiliaUseCase.execute(id, request.getNome());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar família")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        ativarFamiliaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar família")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        desativarFamiliaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}