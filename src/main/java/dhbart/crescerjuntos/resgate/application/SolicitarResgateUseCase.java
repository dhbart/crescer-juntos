package dhbart.crescerjuntos.resgate.application;

import dhbart.crescerjuntos.execucaotarefa.infrastructure.persistence.JpaExecucaoTarefaRepository;
import dhbart.crescerjuntos.recompensa.domain.repository.RecompensaRepository;
import dhbart.crescerjuntos.resgate.application.dto.ResgateRequest;
import dhbart.crescerjuntos.resgate.application.dto.ResgateResponse;
import dhbart.crescerjuntos.resgate.domain.model.Resgate;
import dhbart.crescerjuntos.resgate.domain.repository.ResgateRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.shared.domain.exception.PontosInsuficientesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitarResgateUseCase {
    private final ResgateRepository resgateRepository;
    private final RecompensaRepository recompensaRepository;
    private final JpaExecucaoTarefaRepository jpaExecucaoRepository;

    public ResgateResponse execute(ResgateRequest request) {
        var recompensa = recompensaRepository.buscarPorId(request.getRecompensaId())
                .orElseThrow(() -> ResourceNotFoundException.recompensa(request.getRecompensaId()));

        int pontosDisponiveis = jpaExecucaoRepository.somarPontosPorCrianca(request.getCriancaId());
        int pontosNecessarios = recompensa.getCustoPontos();

        if (pontosDisponiveis < pontosNecessarios)
            throw new PontosInsuficientesException(pontosDisponiveis, pontosNecessarios);

        var resgate = new Resgate(request.getCriancaId(), request.getRecompensaId(), pontosNecessarios);
        var salvo = resgateRepository.salvar(resgate);
        return ResgateResponse.from(salvo);
    }
}