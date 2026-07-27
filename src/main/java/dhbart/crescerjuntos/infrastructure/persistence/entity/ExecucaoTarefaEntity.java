package dhbart.crescerjuntos.infrastructure.persistence.entity;

import dhbart.crescerjuntos.domain.model.StatusExecucao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "execucoes_tarefas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExecucaoTarefaEntity {

    private static final int PONTOS_MAXIMOS = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Tarefa é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_id", nullable = false)
    private TarefaEntity tarefa;

    @NotNull(message = "Criança é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crianca_id", nullable = false)
    private CriancaEntity crianca;

    @NotNull(message = "Data de execução é obrigatória")
    @Column(nullable = false)
    private LocalDate dataExecucao;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusExecucao status = StatusExecucao.PENDENTE;

    @Min(value = 0, message = "Pontos não podem ser negativos")
    @Max(value = PONTOS_MAXIMOS, message = "Pontos máximos: " + PONTOS_MAXIMOS)
    @Column(nullable = false)
    @Builder.Default
    private int pontosAplicados = 0;

    @Column(length = 500)
    private String observacao;

    private LocalDateTime registradoEm;

    private LocalDateTime avaliadoEm;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}