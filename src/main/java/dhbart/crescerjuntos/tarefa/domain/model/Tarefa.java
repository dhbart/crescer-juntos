package dhbart.crescerjuntos.tarefa.domain.model;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tarefa {

    private static final int PONTOS_MAXIMOS = 100;

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    private String titulo;
    private String descricao;
    @Setter(AccessLevel.NONE)
    private Frequencia frequencia;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private List<DayOfWeek> diasDaSemana = new ArrayList<>();

    private Integer diaDoMes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horarioPreferido;
    private int pontos;
    private boolean ativa;
    @Setter(AccessLevel.NONE)
    private Long familiaId;

    public Tarefa(String titulo, Frequencia frequencia, Long familiaId, int pontos) {
        this.id = null;
        this.titulo = validarTexto(titulo);
        this.descricao = null;
        this.frequencia = Objects.requireNonNull(frequencia, "Frequência é obrigatória");
        this.diasDaSemana = new ArrayList<>();
        this.diaDoMes = null;
        this.dataInicio = null;
        this.dataFim = null;
        this.horarioPreferido = null;
        this.pontos = validarPontos(pontos);
        this.ativa = true;
        this.familiaId = Objects.requireNonNull(familiaId, "ID da família é obrigatório");

    }

    public void atualizarDados(
            String titulo,
            String descricao,
            Frequencia frequencia,
            List<DayOfWeek> diasDaSemana,
            Integer diaDoMes,
            LocalDate dataInicio,
            LocalDate dataFim,
            LocalTime horarioPreferido,
            int pontos
    ) {
        this.titulo = validarTexto(titulo);
        this.descricao = descricao;
        this.frequencia = Objects.requireNonNull(frequencia, "Frequência é obrigatória");
        this.diasDaSemana = new ArrayList<>();
        this.diaDoMes = null;
        this.dataInicio = null;
        this.dataFim = null;
        this.horarioPreferido = horarioPreferido;
        this.pontos = validarPontos(pontos);

        switch (frequencia) {
            case SEMANAL -> configurarDiasDaSemana(diasDaSemana);
            case MENSAL -> {
                if (diaDoMes == null) {
                    throw new IllegalArgumentException("Dia do mês é obrigatório");
                }
                configurarDiaDoMes(diaDoMes);
            }
            case PERIODO_PERSONALIZADO -> configurarPeriodoPersonalizado(dataInicio, dataFim);
            case DIARIA -> {
                // Não exige configuração adicional.
            }
        }
    }


    public List<DayOfWeek> getDiasDaSemana() {
        return Collections.unmodifiableList(diasDaSemana);
    }

    public void configurarDiasDaSemana(List<DayOfWeek> dias) {
        if (frequencia != Frequencia.SEMANAL) {
            throw new IllegalStateException("Dias da semana só podem ser configurados para frequência SEMANAL");
        }
        if (dias == null || dias.isEmpty()) {
            throw new IllegalArgumentException("Deve selecionar pelo menos um dia da semana");
        }
        this.diasDaSemana = new ArrayList<>(dias);
    }

    public void configurarDiaDoMes(int dia) {
        if (frequencia != Frequencia.MENSAL) {
            throw new IllegalStateException("Dia do mês só pode ser configurado para frequência MENSAL");
        }
        if (dia < 1 || dia > 31) {
            throw new IllegalArgumentException("Dia do mês deve ser entre 1 e 31");
        }
        this.diaDoMes = dia;
    }

    public void configurarPeriodoPersonalizado(LocalDate inicio, LocalDate fim) {
        if (frequencia != Frequencia.PERIODO_PERSONALIZADO) {
            throw new IllegalStateException("Período só pode ser configurado para frequência PERIODO_PERSONALIZADO");
        }
        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new IllegalArgumentException("Período personalizado inválido");
        }
        this.dataInicio = inicio;
        this.dataFim = fim;
    }

    public boolean estaAtivaNaData(LocalDate data) {
        if (!ativa || data == null || (dataInicio != null && data.isBefore(dataInicio))
                || (dataFim != null && data.isAfter(dataFim))) {
            return false;
        }
        return switch (frequencia) {
            case DIARIA -> true;
            case SEMANAL -> diasDaSemana.contains(data.getDayOfWeek());
            case MENSAL -> diaDoMes != null && data.getDayOfMonth() == diaDoMes;
            case PERIODO_PERSONALIZADO -> dataInicio != null && dataFim != null;
        };
    }

    public void desativar() {
        this.ativa = false;
    }

    public void ativar() {
        this.ativa = true;
    }

    public void atualizarPontos(int novosPontos) {
        this.pontos = validarPontos(novosPontos);
    }

    private static int validarPontos(int pontos) {
        if (pontos < 0 || pontos > PONTOS_MAXIMOS) {
            throw new IllegalArgumentException("Pontos devem estar entre 0 e " + PONTOS_MAXIMOS);
        }
        return pontos;
    }

    private static String validarTexto(String valor) {
        Objects.requireNonNull(valor, "Título" + " é obrigatório");
        if (valor.isBlank()) throw new IllegalArgumentException("Título" + " não pode ser vazio");
        return valor;
    }
}