package dhbart.crescerjuntos.criancafamilia.infrastructure.web;

import dhbart.crescerjuntos.criancafamilia.application.*;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaRequest;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaResponse;
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

    private final CadastrarCriancaUseCase cadastrarCriancaUseCase;
    private final BuscarCriancaUseCase buscarCriancaUseCase;
    private final ListarCriancasPorFamiliaUseCase listarCriancasPorFamiliaUseCase;
    private final AtualizarApelidoCriancaUseCase atualizarApelidoCriancaUseCase;
    private final AtivarCriancaUseCase ativarCriancaUseCase;
    private final DesativarCriancaUseCase desativarCriancaUseCase;

    @PostMapping
    @Operation(summary = "Criar nova crianca")
    public ResponseEntity<CriancaResponse> criar(@Valid @RequestBody CriancaRequest request) {
        CriancaResponse response = cadastrarCriancaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar crianca por ID")
    public ResponseEntity<CriancaResponse> buscarPorId(@PathVariable Long id) {
        CriancaResponse response = buscarCriancaUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar criancas")
    public ResponseEntity<List<CriancaResponse>> listar(
            @RequestParam(required = false) Long familiaId,
            @RequestParam(required = false, defaultValue = "false") boolean apenasAtivas) {
        List<CriancaResponse> response;
        if (familiaId != null) {
            response = listarCriancasPorFamiliaUseCase.execute(familiaId, apenasAtivas);
        } else {
            response = List.of();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar apelido da crianca")
    public ResponseEntity<CriancaResponse> atualizar(
            @PathVariable Long id,
            @RequestParam String novoApelido) {
        CriancaResponse response = atualizarApelidoCriancaUseCase.execute(id, novoApelido);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar crianca")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        ativarCriancaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar crianca")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        desativarCriancaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}