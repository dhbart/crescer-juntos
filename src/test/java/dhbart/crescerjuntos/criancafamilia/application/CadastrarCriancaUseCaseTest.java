package dhbart.crescerjuntos.criancafamilia.application;

import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaRequest;
import dhbart.crescerjuntos.criancafamilia.application.dto.CriancaResponse;
import dhbart.crescerjuntos.criancafamilia.domain.model.Crianca;
import dhbart.crescerjuntos.criancafamilia.domain.repository.CriancaRepository;
import dhbart.crescerjuntos.criancafamilia.domain.repository.FamiliaRepository;
import dhbart.crescerjuntos.shared.domain.exception.ResourceNotFoundException;
import dhbart.crescerjuntos.shared.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarCriancaUseCaseTest {

    @Mock
    private CriancaRepository criancaRepository;

    @Mock
    private FamiliaRepository familiaRepository;

    @InjectMocks
    private CadastrarCriancaUseCase cadastrarCriancaUseCase;

    @Test
    void deveLancarExcecaoQuandoFamiliaNaoExiste() {
        CriancaRequest request = new CriancaRequest();
        request.setApelido("Joãozinho");
        request.setDataNascimento(LocalDate.of(2018, 5, 10));
        request.setFamiliaId(999L);

        when(familiaRepository.existe(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                cadastrarCriancaUseCase.execute(request));

        verify(criancaRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoCriancaDuplicada() {
        CriancaRequest request = new CriancaRequest();
        request.setApelido("Maria");
        request.setDataNascimento(LocalDate.of(2019, 3, 15));
        request.setFamiliaId(1L);

        when(familiaRepository.existe(1L)).thenReturn(true);
        when(criancaRepository.existePorApelidoEDataNascimentoEFamilia(
                "Maria", LocalDate.of(2019, 3, 15), 1L)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                cadastrarCriancaUseCase.execute(request));

        assertTrue(exception.getMessage().contains("Maria"));
        assertTrue(exception.getMessage().contains("2019-03-15"));

        verify(criancaRepository, never()).salvar(any());
    }

    @Test
    void deveCadastrarCriancaComSucessoQuandoNaoDuplicada() {
        CriancaRequest request = new CriancaRequest();
        request.setApelido("Pedro");
        request.setDataNascimento(LocalDate.of(2020, 7, 20));
        request.setFamiliaId(1L);

        Crianca criancaSalva = new Crianca(
                "Pedro",
                LocalDate.of(2020, 7, 20),
                1L
        );

        when(familiaRepository.existe(1L)).thenReturn(true);

        when(criancaRepository.existePorApelidoEDataNascimentoEFamilia(
                "Pedro",
                LocalDate.of(2020, 7, 20),
                1L
        )).thenReturn(false);

        when(criancaRepository.salvar(any(Crianca.class)))
                .thenReturn(criancaSalva);

        CriancaResponse response = cadastrarCriancaUseCase.execute(request);

        assertNotNull(response);
        verify(criancaRepository).salvar(any(Crianca.class));
    }
}