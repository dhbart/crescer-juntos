package dhbart.crescerjuntos.infrastructure.web;

import dhbart.crescerjuntos.application.dto.execucao.AprovarExecucaoTarefaDTO;
import dhbart.crescerjuntos.application.dto.execucao.ExecucaoTarefaCreateDTO;
import dhbart.crescerjuntos.application.dto.execucao.ExecucaoTarefaResponseDTO;
import dhbart.crescerjuntos.application.dto.execucao.RejeitarExecucaoTarefaDTO;
import dhbart.crescerjuntos.application.dto.mapper.ExecucaoTarefaMapper;
import dhbart.crescerjuntos.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.domain.model.StatusExecucao;
import dhbart.crescerjuntos.domain.service.ExecucaoTarefaService;
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
@RequestMapping("/api/execucoes-tarefas")
@RequiredArgsConstructor
@Tag(name = "Execucoes de Tarefas", description = "Gerenciamento de execucoes de tarefas")
public class ExecucaoTarefaController {

    private final ExecucaoTarefaService execucaoTarefaService;
    private final ExecucaoTarefaMapper execucaoMapper;

    @PostMapping
    @Operation(summary = "Registrar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponseDTO> registrar(@Valid @RequestBody ExecucaoTarefaCreateDTO dto) {
        ExecucaoTarefa execucao = execucaoTarefaService.registrar(
                dto.getTarefaId(), dto.getCriancaId(), dto.getDataExecucao(), dto.getObservacao());
        return ResponseEntity.status(HttpStatus.CREATED).body(execucaoMapper.toResponse(execucao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar execucao por ID")
    public ResponseEntity<ExecucaoTarefaResponseDTO> buscarPorId(@PathVariable Long id) {
        ExecucaoTarefa execucao = execucaoTarefaService.buscarPorId(id);
        return ResponseEntity.ok(execucaoMapper.toResponse(execucao));
    }

    @GetMapping("/crianca/{criancaId}")
    @Operation(summary = "Listar execucoes por crianca")
    public ResponseEntity<List<ExecucaoTarefaResponseDTO>> listarPorCrianca(@PathVariable Long criancaId) {
        List<ExecucaoTarefa> execucoes = execucaoTarefaService.listarPorCrianca(criancaId);
        List<ExecucaoTarefaResponseDTO> response = execucoes.stream().map(execucaoMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/crianca/{criancaId}/status/{status}")
    @Operation(summary = "Listar execucoes por crianca e status")
    public ResponseEntity<List<ExecucaoTarefaResponseDTO>> listarPorCriancaEStatus(
            @PathVariable Long criancaId, @PathVariable StatusExecucao status) {
        List<ExecucaoTarefa> execucoes = execucaoTarefaService.listarPorCriancaEStatus(criancaId, status);
        List<ExecucaoTarefaResponseDTO> response = execucoes.stream().map(execucaoMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/crianca/{criancaId}/periodo")
    @Operation(summary = "Listar execucoes por crianca e periodo")
    public ResponseEntity<List<ExecucaoTarefaResponseDTO>> listarPorPeriodo(
            @PathVariable Long criancaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<ExecucaoTarefa> execucoes = execucaoTarefaService.listarPorPeriodo(criancaId, dataInicio, dataFim);
        List<ExecucaoTarefaResponseDTO> response = execucoes.stream().map(execucaoMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponseDTO> aprovar(
            @PathVariable Long id, @Valid @RequestBody AprovarExecucaoTarefaDTO dto) {
        ExecucaoTarefa execucao = execucaoTarefaService.aprovar(id, dto.getPontos());
        return ResponseEntity.ok(execucaoMapper.toResponse(execucao));
    }

    @PatchMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponseDTO> rejeitar(
            @PathVariable Long id, @Valid @RequestBody RejeitarExecucaoTarefaDTO dto) {
        ExecucaoTarefa execucao = execucaoTarefaService.rejeitar(id, dto.getMotivo());
        return ResponseEntity.ok(execucaoMapper.toResponse(execucao));
    }

    @PatchMapping("/{id}/expirar")
    @Operation(summary = "Expirar execucao de tarefa")
    public ResponseEntity<ExecucaoTarefaResponseDTO> expirar(@PathVariable Long id) {
        ExecucaoTarefa execucao = execucaoTarefaService.expirar(id);
        return ResponseEntity.ok(execucaoMapper.toResponse(execucao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir execucao de tarefa")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        execucaoTarefaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
