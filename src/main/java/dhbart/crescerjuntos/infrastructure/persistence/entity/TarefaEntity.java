package dhbart.crescerjuntos.infrastructure.persistence.entity;

import dhbart.crescerjuntos.domain.model.Frequencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarefas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TarefaEntity {

    private static final int PONTOS_MAXIMOS = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @NotNull(message = "Frequência é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequencia frequencia;

    @ElementCollection
    @CollectionTable(name = "tarefa_dias_semana", joinColumns = @JoinColumn(name = "tarefa_id"))
    @Column(name = "dia_semana")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<DayOfWeek> diasDaSemana = new ArrayList<>();

    @Column(name = "dia_do_mes")
    private Integer diaDoMes;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private LocalTime horarioPreferido;

    @Min(value = 0, message = "Pontos não podem ser negativos")
    @Max(value = PONTOS_MAXIMOS, message = "Pontos máximos: " + PONTOS_MAXIMOS)
    @Column(nullable = false)
    private int pontos;

    @Column(nullable = false)
    @Builder.Default
    private boolean ativa = true;

    @NotNull(message = "Família é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familia_id", nullable = false)
    private FamiliaEntity familia;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;
}