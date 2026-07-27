package dhbart.crescerjuntos.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Familia {

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;
    private String nome;
    private boolean ativa;
    
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private List<Crianca> criancas = new ArrayList<>();

    public Familia(String nome) {
        this.id = null;
        this.nome = validarNome(nome);
        this.ativa = true;
        this.criancas = new ArrayList<>();
    }

    public List<Crianca> getCriancas() {
        return Collections.unmodifiableList(criancas);
    }

    public void adicionarCrianca(Crianca crianca) {
        Objects.requireNonNull(crianca, "Criança não pode ser nula");
        if (criancas.contains(crianca)) {
            throw new IllegalArgumentException("Criança já pertence a esta família");
        }
        criancas.add(crianca);
    }

    public void removerCrianca(Crianca crianca) {
        if (!criancas.remove(crianca)) {
            throw new IllegalArgumentException("Criança não encontrada nesta família");
        }
    }

    public void desativar() {
        this.ativa = false;
    }

    public void ativar() {
        this.ativa = true;
    }

    public void atualizarNome(String novoNome) {
        this.nome = validarNome(novoNome);
    }

    private static String validarNome(String nome) {
        Objects.requireNonNull(nome, "Nome da família é obrigatório");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome da família não pode ser vazio");
        }
        return nome;
    }
}