package dhbart.crescerjuntos.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TarefaTest {

    @Test
    void deveCriarTarefaValida() {
        Tarefa tarefa = new Tarefa("Limpar quarto", Frequencia.DIARIA, 1L, 10);
        
        assertNull(tarefa.getId());
        assertEquals("Limpar quarto", tarefa.getTitulo());
        assertEquals(Frequencia.DIARIA, tarefa.getFrequencia());
        assertEquals(1L, tarefa.getFamiliaId());
        assertEquals(10, tarefa.getPontos());
        assertTrue(tarefa.isAtiva());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void deveLancarExcecaoParaTituloInvalido(String titulo) {
        assertThrows(RuntimeException.class,
                () -> new Tarefa(titulo, Frequencia.DIARIA, 1L, 10));
    }

    @Test
    void deveLancarExcecaoParaFrequenciaNula() {
        assertThrows(NullPointerException.class,
                () -> new Tarefa("Tarefa", null, 1L, 10));
    }

    @Test
    void deveLancarExcecaoParaFamiliaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new Tarefa("Tarefa", Frequencia.DIARIA, null, 10));
    }

    @Test
    void deveLancarExcecaoParaPontosNegativos() {
        assertThrows(RuntimeException.class,
                () -> new Tarefa("Tarefa", Frequencia.DIARIA, 1L, -1));
    }

    @Test
    void deveLancarExcecaoParaPontosAcimaDe100() {
        assertThrows(RuntimeException.class,
                () -> new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 101));
    }

    @Test
    void deveConfigurarDiasDaSemana() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.SEMANAL, 1L, 10);
        tarefa.configurarDiasDaSemana(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        
        assertEquals(2, tarefa.getDiasDaSemana().size());
        assertTrue(tarefa.getDiasDaSemana().contains(DayOfWeek.MONDAY));
    }

    @Test
    void deveLancarExcecaoAoConfigurarDiasDaSemanaParaFrequenciaNaoSemanal() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        assertThrows(IllegalStateException.class,
                () -> tarefa.configurarDiasDaSemana(List.of(DayOfWeek.MONDAY)));
    }

    @Test
    void deveConfigurarDiaDoMes() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.MENSAL, 1L, 10);
        tarefa.configurarDiaDoMes(15);
        assertEquals(15, tarefa.getDiaDoMes());
    }

    @Test
    void deveLancarExcecaoAoConfigurarDiaDoMesParaFrequenciaNaoMensal() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        assertThrows(IllegalStateException.class,
                () -> tarefa.configurarDiaDoMes(15));
    }

    @Test
    void deveConfigurarPeriodoPersonalizado() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.PERIODO_PERSONALIZADO, 1L, 10);
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 12, 31);
        tarefa.configurarPeriodoPersonalizado(inicio, fim);
        
        assertEquals(inicio, tarefa.getDataInicio());
        assertEquals(fim, tarefa.getDataFim());
    }

    @Test
    void deveLancarExcecaoAoConfigurarPeriodoParaFrequenciaIncompativel() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        assertThrows(IllegalStateException.class,
                () -> tarefa.configurarPeriodoPersonalizado(LocalDate.now(), LocalDate.now().plusDays(1)));
    }

    @Test
    void deveAtivarEDesativar() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        assertTrue(tarefa.isAtiva());

        tarefa.desativar();
        assertFalse(tarefa.isAtiva());

        tarefa.ativar();
        assertTrue(tarefa.isAtiva());
    }

    @Test
    void deveAtualizarPontos() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        tarefa.atualizarPontos(20);
        assertEquals(20, tarefa.getPontos());
    }

    @Test
    void deveAtualizarDados() {
        Tarefa tarefa = new Tarefa("Original", Frequencia.DIARIA, 1L, 10);
        tarefa.setDescricao("Descrição original");
        
        tarefa.atualizarDados(
                "Atualizado",
                "Nova descrição",
                Frequencia.SEMANAL,
                List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY),
                null, null, null,
                LocalTime.of(10, 0),
                20
        );

        assertEquals("Atualizado", tarefa.getTitulo());
        assertEquals("Nova descrição", tarefa.getDescricao());
        assertEquals(Frequencia.SEMANAL, tarefa.getFrequencia());
        assertEquals(2, tarefa.getDiasDaSemana().size());
        assertEquals(20, tarefa.getPontos());
        assertEquals(LocalTime.of(10, 0), tarefa.getHorarioPreferido());
    }

    @Test
    void tarefaDiariaDeveEstarAtivaEmQualquerData() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        
        assertTrue(tarefa.estaAtivaNaData(LocalDate.now()));
        assertTrue(tarefa.estaAtivaNaData(LocalDate.now().plusDays(30)));
    }

    @Test
    void tarefaInativaNaoDeveEstarAtivaEmData() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.DIARIA, 1L, 10);
        tarefa.desativar();
        
        assertFalse(tarefa.estaAtivaNaData(LocalDate.now()));
    }

    @Test
    void tarefaComPeriodoNaoDeveEstarAtivaForaDoPeriodo() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.PERIODO_PERSONALIZADO, 1L, 10);
        tarefa.configurarPeriodoPersonalizado(
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30)
        );
        
        assertFalse(tarefa.estaAtivaNaData(LocalDate.of(2025, 5, 31)));
        assertTrue(tarefa.estaAtivaNaData(LocalDate.of(2025, 6, 15)));
        assertFalse(tarefa.estaAtivaNaData(LocalDate.of(2025, 7, 1)));
    }

    @Test
    void deveManterDiasDaSemanaComoListaNaoModificavel() {
        Tarefa tarefa = new Tarefa("Tarefa", Frequencia.SEMANAL, 1L, 10);
        tarefa.configurarDiasDaSemana(List.of(DayOfWeek.MONDAY));
        
        assertThrows(UnsupportedOperationException.class,
                () -> tarefa.getDiasDaSemana().clear());
    }
}