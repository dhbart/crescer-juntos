package dhbart.crescerjuntos.domain.model;

import dhbart.crescerjuntos.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResgateTest {

    @Test
    void deveCriarResgateSolicitado() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        
        assertNull(resgate.getId());
        assertEquals(10L, resgate.getCriancaId());
        assertEquals(5L, resgate.getRecompensaId());
        assertEquals(50, resgate.getPontosUtilizados());
        assertEquals(StatusResgate.SOLICITADO, resgate.getStatus());
        assertTrue(resgate.isSolicitado());
        assertFalse(resgate.isAprovado());
    }

    @Test
    void deveLancarExcecaoParaCriancaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new Resgate(null, 5L, 50));
    }

    @Test
    void deveLancarExcecaoParaRecompensaIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new Resgate(10L, null, 50));
    }

    @Test
    void deveLancarExcecaoParaPontosNegativos() {
        assertThrows(IllegalArgumentException.class,
                () -> new Resgate(10L, 5L, -1));
    }

    @Test
    void deveAprovarResgate() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        resgate.aprovar();
        
        assertEquals(StatusResgate.APROVADO, resgate.getStatus());
        assertTrue(resgate.isAprovado());
        assertFalse(resgate.isSolicitado());
    }

    @Test
    void deveLancarExcecaoAoAprovarResgateNaoSolicitado() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        resgate.aprovar();
        
        assertThrows(IllegalStateException.class, () -> resgate.aprovar());
    }

    @Test
    void deveRejeitarResgate() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        resgate.rejeitar("Pontos insuficientes");
        
        assertEquals(StatusResgate.REJEITADO, resgate.getStatus());
        assertEquals("Pontos insuficientes", resgate.getObservacao());
    }

    @Test
    void deveLancarExcecaoAoRejeitarResgateNaoSolicitado() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        resgate.aprovar();
        
        assertThrows(IllegalStateException.class, () -> resgate.rejeitar("Motivo"));
    }

    @Test
    void deveEntregarResgateAprovado() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        resgate.aprovar();
        resgate.entregar();
        
        assertEquals(StatusResgate.ENTREGUE, resgate.getStatus());
    }

    @Test
    void deveLancarExcecaoAoEntregarResgateNaoAprovado() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        assertThrows(IllegalStateException.class, () -> resgate.entregar());
    }

    @Test
    void deveTransitarSolicitadoParaAprovadoParaEntregue() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        
        assertEquals(StatusResgate.SOLICITADO, resgate.getStatus());
        
        resgate.aprovar();
        assertEquals(StatusResgate.APROVADO, resgate.getStatus());
        
        resgate.entregar();
        assertEquals(StatusResgate.ENTREGUE, resgate.getStatus());
    }

    @Test
    void deveTransitarSolicitadoParaRejeitado() {
        Resgate resgate = new Resgate(10L, 5L, 50);
        resgate.rejeitar("Indisponível");
        
        assertEquals(StatusResgate.REJEITADO, resgate.getStatus());
    }
}