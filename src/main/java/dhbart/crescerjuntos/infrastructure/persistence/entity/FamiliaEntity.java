package dhbart.crescerjuntos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "familias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FamiliaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Nome da família é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Builder.Default
    private boolean ativa = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CriancaEntity> criancas = new ArrayList<>();
}