package dhbart.crescerjuntos.recompensa.infrastructure.persistence.entity;

import dhbart.crescerjuntos.criancafamilia.infrastructure.persistence.entity.FamiliaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "recompensas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecompensaEntity {
    private static final int CUSTO_MAXIMO = 1_000;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include private Long id;
    @NotBlank @Column(nullable = false) private String nome;
    @Column(length = 500) private String descricao;
    @Min(1) @Max(CUSTO_MAXIMO) @Column(nullable = false) private int custoPontos;
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familia_id", nullable = false) private FamiliaEntity familia;
    @Column(nullable = false) @Builder.Default private boolean disponivel = true;
    @CreationTimestamp @Column(nullable = false, updatable = false) private LocalDateTime criadoEm;
    @UpdateTimestamp private LocalDateTime atualizadoEm;
}