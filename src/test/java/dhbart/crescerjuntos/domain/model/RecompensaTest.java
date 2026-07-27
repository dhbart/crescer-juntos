package dhbart.crescerjuntos.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class RecompensaTest {

    @Test
    void deveCriarRecompensaValida() {
        Recompensa recompensa = new Recompensa("Videogame", 50, 1L);
        
        assertNull(recompensa.getId());
        assertEquals("Videogame", recompensa.getNome());
        assertEquals(50, recompensa.getCustoPontos());
        assertEquals(1L, recompensa.getFamiliaId());
        assertTrue(recompensa.isDisponivel());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void deveLancarExcecaoParaNomeInvalido(String nome) {
        assertThrows(RuntimeException.class,
                () -> new Recompensa(nome, 50, 1L));
    }

    @Test
    void deveLancarExcecaoParaCustoMenorQue1() {
        assertThrows(RuntimeException.class,
                () -> new Recompensa("Videogame", 0, 1L));
    }

    @Test
    void deveLancarExcecaoParaCustoMaiorQue1000() {
        assertThrows(RuntimeException.class,
                () -> new Recompensa("Videogame", 1001, 1L));
    }

    @Test
    void deveLancarExcecaoParaFamiliaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new Recompensa("Videogame", 50, null));
    }

    @Test
    void deveAtivarEDesativar() {
        Recompensa recompensa = new Recompensa("Videogame", 50, 1L);
        assertTrue(recompensa.isDisponivel());

        recompensa.desativar();
        assertFalse(recompensa.isDisponivel());

        recompensa.ativar();
        assertTrue(recompensa.isDisponivel());
    }

    @Test
    void deveAtualizarCusto() {
        Recompensa recompensa = new Recompensa("Videogame", 50, 1L);
        recompensa.atualizarCusto(100);
        assertEquals(100, recompensa.getCustoPontos());
    }

    @Test
    void deveLancarExcecaoAoAtualizarCustoInvalido() {
        Recompensa recompensa = new Recompensa("Videogame", 50, 1L);
        assertThrows(RuntimeException.class,
                () -> recompensa.atualizarCusto(-1));
    }
}