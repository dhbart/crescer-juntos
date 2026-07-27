package dhbart.crescerjuntos.domain.exception;

import lombok.Getter;

@Getter
public class PontosInsuficientesException extends BusinessException {

    private final int pontosDisponiveis;
    private final int pontosNecessarios;

    public PontosInsuficientesException(
            int pontosDisponiveis,
            int pontosNecessarios
    ) {
        super(String.format(
                "Pontos insuficientes. Disponíveis: %d. Necessários: %d.",
                pontosDisponiveis,
                pontosNecessarios
        ));

        this.pontosDisponiveis = pontosDisponiveis;
        this.pontosNecessarios = pontosNecessarios;
    }

}
