package dhbart.crescerjuntos.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CriancaTest {

    @Test
    void deveCriarCriancaValida() {
        Crianca crianca = new Crianca("Joãozinho", LocalDate.of(2018, 5, 10), 1L);
        
        assertNull(crianca.getId());
        assertEquals("Joãozinho", crianca.getApelido());
        assertEquals(LocalDate.of(2018, 5, 10), crianca.getDataNascimento());
        assertEquals(1L, crianca.getFamiliaId());
        assertTrue(crianca.isAtiva());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void deveLancarExcecaoParaApelidoInvalido(String apelidoInvalido) {
        assertThrows(RuntimeException.class, () ->
                new Crianca(apelidoInvalido, LocalDate.of(2018, 5, 10), 1L));
    }

    @Test
    void deveLancarExcecaoParaDataNascimentoNula() {
        assertThrows(NullPointerException.class, () ->
                new Crianca("Joãozinho", null, 1L));
    }

    @Test
    void deveLancarExcecaoParaFamiliaIdNulo() {
        assertThrows(NullPointerException.class, () ->
                new Crianca("Joãozinho", LocalDate.of(2018, 5, 10), null));
    }

    @Test
    void deveCalcularIdadeCorretamente() {
        Crianca crianca = new Crianca("Maria", LocalDate.of(2015, 1, 1), 1L);
        int idadeEsperada = java.time.Period.between(LocalDate.of(2015, 1, 1), LocalDate.now()).getYears();
        assertEquals(idadeEsperada, crianca.getIdade());
    }

    @Test
    void deveAtualizarApelido() {
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);
        crianca.atualizarApelido("João Pedro");
        assertEquals("João Pedro", crianca.getApelido());
    }

    @Test
    void deveLancarExcecaoAoAtualizarApelidoInvalido() {
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);
        assertThrows(RuntimeException.class, () ->
                crianca.atualizarApelido(""));
    }

    @Test
    void deveAtivarEDesativar() {
        Crianca crianca = new Crianca("João", LocalDate.of(2018, 5, 10), 1L);
        assertTrue(crianca.isAtiva());

        crianca.desativar();
        assertFalse(crianca.isAtiva());

        crianca.ativar();
        assertTrue(crianca.isAtiva());
    }

    @Test
    void equalsSegueConfiguracaoAtualDoModelo() {
        Crianca crianca1 = Crianca.builder().id(1L).apelido("João").build();
        Crianca crianca2 = Crianca.builder().id(1L).apelido("Maria").build();
        Crianca crianca3 = Crianca.builder().id(2L).apelido("João").build();

        assertEquals(crianca1, crianca2);
        assertEquals(crianca1, crianca3);
    }
}