package dhbart.crescerjuntos.recompensa.domain.model;

import lombok.*;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recompensa {

    private static final int CUSTO_MAXIMO = 1_000;

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;
    private String nome;
    private String descricao;
    private int custoPontos;
    @Setter(AccessLevel.NONE)
    private Long familiaId;
    private boolean disponivel;

    public Recompensa(String nome, int custoPontos, Long familiaId) {
        this.id = null;
        this.nome = validarNome(nome);
        this.descricao = null;
        this.custoPontos = validarCusto(custoPontos);
        this.familiaId = Objects.requireNonNull(familiaId, "ID da família é obrigatório");
        this.disponivel = true;
    }

    public void atualizarCusto(int novoCusto) { this.custoPontos = validarCusto(novoCusto); }
    public void desativar() { this.disponivel = false; }
    public void ativar() { this.disponivel = true; }

    private static String validarNome(String nome) {
        Objects.requireNonNull(nome, "Nome é obrigatório");
        if (nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        return nome;
    }

    private static int validarCusto(int custo) {
        if (custo <= 0 || custo > CUSTO_MAXIMO)
            throw new IllegalArgumentException("Custo em pontos deve estar entre 1 e " + CUSTO_MAXIMO);
        return custo;
    }
}