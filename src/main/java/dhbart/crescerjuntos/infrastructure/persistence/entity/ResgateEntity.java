package dhbart.crescerjuntos.infrastructure.persistence.entity;

import dhbart.crescerjuntos.domain.model.StatusResgate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "resgates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResgateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Criança é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crianca_id", nullable = false)
    private CriancaEntity crianca;

    @NotNull(message = "Recompensa é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recompensa_id", nullable = false)
    private RecompensaEntity recompensa;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusResgate status = StatusResgate.SOLICITADO;

    @Min(value = 0, message = "Pontos utilizados não podem ser negativos")
    @Column(nullable = false)
    private int pontosUtilizados;

    @Column(length = 500)
    private String observacao;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime solicitadoEm;

    private LocalDateTime avaliadoEm;

    private LocalDateTime entregueEm;
}