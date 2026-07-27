package dhbart.crescerjuntos.criancafamilia.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Crianca {

    @Setter(AccessLevel.NONE)
    private Long id;
    private String apelido;
    private LocalDate dataNascimento;
    private Long familiaId;
    private boolean ativa;

    public Crianca(String apelido, LocalDate dataNascimento, Long familiaId) {
        this.id = null;
        this.apelido = validarApelido(apelido);
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Data de nascimento é obrigatória");
        this.familiaId = Objects.requireNonNull(familiaId, "ID da família é obrigatório");
        this.ativa = true;
    }

    public int getIdade() {
        return java.time.Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public void atualizarApelido(String novoApelido) {
        this.apelido = validarApelido(novoApelido);
    }

    public void desativar() {
        this.ativa = false;
    }

    public void ativar() {
        this.ativa = true;
    }

    private static String validarApelido(String apelido) {
        Objects.requireNonNull(apelido, "Apelido é obrigatório");
        if (apelido.isBlank()) {
            throw new IllegalArgumentException("Apelido não pode ser vazio");
        }
        return apelido;
    }
}