package dhbart.crescerjuntos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "criancas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CriancaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Apelido é obrigatório")
    @Column(nullable = false)
    private String apelido;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "Família é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familia_id", nullable = false)
    private FamiliaEntity familia;

    @Column(nullable = false)
    @Builder.Default
    private boolean ativa = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;
}