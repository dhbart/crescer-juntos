package dhbart.crescerjuntos.domain.model;

import dhbart.crescerjuntos.execucaotarefa.domain.model.ExecucaoTarefa;
import dhbart.crescerjuntos.execucaotarefa.domain.model.StatusExecucao;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExecucaoTarefaTest {

    @Test
    void deveCriarExecucaoPendente() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.of(2025, 6, 15));
        
        assertNull(execucao.getId());
        assertEquals(1L, execucao.getTarefaId());
        assertEquals(10L, execucao.getCriancaId());
        assertEquals(LocalDate.of(2025, 6, 15), execucao.getDataExecucao());
        assertEquals(StatusExecucao.PENDENTE, execucao.getStatus());
        assertTrue(execucao.isPendente());
        assertFalse(execucao.isAprovada());
    }

    @Test
    void deveRegistrarExecucao() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        execucao.registrar("Tarefa concluída");
        
        assertEquals(StatusExecucao.REGISTRADA, execucao.getStatus());
        assertEquals("Tarefa concluída", execucao.getObservacao());
    }

    @Test
    void deveLancarExcecaoAoRegistrarExecucaoNaoPendente() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        execucao.registrar("Feito");
        
        assertThrows(IllegalStateException.class, () -> execucao.registrar("Tentar registrar novamente"));
    }

    @Test
    void deveAprovarExecucao() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        execucao.registrar("Concluído");
        
        execucao.aprovar(10);
        
        assertEquals(StatusExecucao.APROVADA, execucao.getStatus());
        assertEquals(10, execucao.getPontosAplicados());
        assertTrue(execucao.isAprovada());
    }

    @Test
    void deveLancarExcecaoAoAprovarExecucaoNaoRegistrada() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        assertThrows(IllegalStateException.class, () -> execucao.aprovar(10));
    }

    @Test
    void deveRejeitarExecucao() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        execucao.registrar("Feito");
        
        execucao.rejeitar("Não atende aos critérios");
        
        assertEquals(StatusExecucao.REJEITADA, execucao.getStatus());
        assertEquals("Não atende aos critérios", execucao.getObservacao());

    }

    @Test
    void deveLancarExcecaoAoRejeitarExecucaoNaoRegistrada() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        assertThrows(IllegalStateException.class, () -> execucao.rejeitar("Motivo"));
    }

    @Test
    void deveExpirarExecucao() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        execucao.expirar();
        
        assertEquals(StatusExecucao.EXPIRADA, execucao.getStatus());
    }

    @Test
    void deveLancarExcecaoAoExpirarExecucaoNaoPendente() {
        ExecucaoTarefa execucao = new ExecucaoTarefa(1L, 10L, LocalDate.now());
        execucao.registrar("Feito");
        
        assertThrows(IllegalStateException.class, () -> execucao.expirar());
    }

    @Test
    void deveLancarExcecaoParaTarefaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new ExecucaoTarefa(null, 10L, LocalDate.now()));
    }

    @Test
    void deveLancarExcecaoParaCriancaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new ExecucaoTarefa(1L, null, LocalDate.now()));
    }

    @Test
    void deveLancarExcecaoParaDataNula() {
        assertThrows(NullPointerException.class,
                () -> new ExecucaoTarefa(1L, 10L, null));
    }
}