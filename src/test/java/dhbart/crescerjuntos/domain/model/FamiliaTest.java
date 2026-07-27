package dhbart.crescerjuntos.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FamiliaTest {

    @Test
    void deveCriarFamiliaValida() {
        Familia familia = new Familia("Silva");
        
        assertNull(familia.getId());
        assertEquals("Silva", familia.getNome());
        assertTrue(familia.isAtiva());
        assertTrue(familia.getCriancas().isEmpty());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void deveLancarExcecaoParaNomeInvalido(String nomeInvalido) {
        assertThrows(RuntimeException.class, () -> new Familia(nomeInvalido));
    }

    @Test
    void deveAtivarEDesativar() {
        Familia familia = new Familia("Silva");
        assertTrue(familia.isAtiva());

        familia.desativar();
        assertFalse(familia.isAtiva());

        familia.ativar();
        assertTrue(familia.isAtiva());
    }

    @Test
    void deveAdicionarCrianca() {
        Familia familia = new Familia("Silva");
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);

        familia.adicionarCrianca(crianca);

        assertEquals(1, familia.getCriancas().size());
        assertTrue(familia.getCriancas().contains(crianca));
    }

    @Test
    void deveLancarExcecaoAoAdicionarCriancaNula() {
        Familia familia = new Familia("Silva");
        assertThrows(NullPointerException.class, () -> familia.adicionarCrianca(null));
    }

    @Test
    void deveLancarExcecaoAoAdicionarCriancaDuplicada() {
        Familia familia = new Familia("Silva");
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);
        familia.adicionarCrianca(crianca);

        assertThrows(IllegalArgumentException.class, () -> familia.adicionarCrianca(crianca));
    }

    @Test
    void deveRemoverCrianca() {
        Familia familia = new Familia("Silva");
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);
        familia.adicionarCrianca(crianca);

        familia.removerCrianca(crianca);

        assertTrue(familia.getCriancas().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoRemoverCriancaInexistente() {
        Familia familia = new Familia("Silva");
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);

        assertThrows(IllegalArgumentException.class, () -> familia.removerCrianca(crianca));
    }

    @Test
    void deveRetornarCriancasComoListaNaoModificavel() {
        Familia familia = new Familia("Silva");
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);
        familia.adicionarCrianca(crianca);

        assertThrows(UnsupportedOperationException.class, () -> familia.getCriancas().clear());
    }

    @Test
    void equalsDeveUsarId() {
        Familia familia1 = Familia.builder().id(1L).nome("Silva").build();
        Familia familia2 = Familia.builder().id(1L).nome("Souza").build();
        Familia familia3 = Familia.builder().id(2L).nome("Silva").build();

        assertEquals(familia1, familia2);
        assertNotEquals(familia1, familia3);
    }
}