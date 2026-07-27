package dhbart.crescerjuntos.application.dto.mapper;

import dhbart.crescerjuntos.application.dto.execucao.ExecucaoTarefaCreateDTO;
import dhbart.crescerjuntos.application.dto.execucao.ExecucaoTarefaResponseDTO;
import dhbart.crescerjuntos.domain.model.ExecucaoTarefa;
import org.springframework.stereotype.Component;

@Component
public class ExecucaoTarefaMapper {

    public ExecucaoTarefa toDomain(ExecucaoTarefaCreateDTO dto) {
        return ExecucaoTarefa.builder()
                .tarefaId(dto.getTarefaId())
                .criancaId(dto.getCriancaId())
                .dataExecucao(dto.getDataExecucao())
                .observacao(dto.getObservacao())
                .build();
    }

    public ExecucaoTarefaResponseDTO toResponse(ExecucaoTarefa domain) {
        return ExecucaoTarefaResponseDTO.builder()
                .id(domain.getId())
                .tarefaId(domain.getTarefaId())
                .criancaId(domain.getCriancaId())
                .dataExecucao(domain.getDataExecucao())
                .status(domain.getStatus())
                .pontosAplicados(domain.getPontosAplicados())
                .observacao(domain.getObservacao())
                .build();
    }
}
