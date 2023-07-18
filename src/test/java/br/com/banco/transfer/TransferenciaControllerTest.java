package br.com.banco.transfer;

import br.com.banco.transfer.domain.Transferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaControllerTest {

    private TransferenciaController transferenciaController;

    @Mock
    private TransferenciaService transferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferenciaController = new TransferenciaController(transferenciaService);
    }

    @Test
    void getAll_DeveRetornarListaDeTransferencias() {
        List<Transferencia> transferencias = new ArrayList<>();
        when(transferenciaService.getAllTransferencias()).thenReturn(transferencias);

        List<Transferencia> result = transferenciaController.getAll();

        assertEquals(transferencias, result);
    }

    @Test
    void getByConta_DeveRetornarTransferenciasPorConta() {
        Long idConta = 1L;
        List<Transferencia> transferencias = new ArrayList<>();
        when(transferenciaService.getTransferenciasPorConta(idConta)).thenReturn(transferencias);

        List<Transferencia> result = transferenciaController.getByConta(idConta);

        assertEquals(transferencias, result);
    }

    @Test
    void getByPeriodo_DeveRetornarTransferenciasPorPeriodo() {
        String dataInicio = "2023-01-01";
        String dataFim = "2023-01-31";
        List<Transferencia> transferencias = new ArrayList<>();
        when(transferenciaService.getTransferenciasPorPeriodo(dataInicio, dataFim)).thenReturn(transferencias);

        List<Transferencia> result = transferenciaController.getByPeriodo(dataInicio, dataFim);

        assertEquals(transferencias, result);
    }

    @Test
    void getByPeriodo_DeveLancarExcecaoQuandoDadosIncompletos() {
        String dataInicio = null;
        String dataFim = "2023-01-31";

        assertThrows(IllegalArgumentException.class, () -> {
            transferenciaController.getByPeriodo(dataInicio, dataFim);
        });
    }

    @Test
    void getByOperador_DeveRetornarTransferenciasPorOperador() {
        String nomeOperador = "João";
        List<Transferencia> transferencias = new ArrayList<>();
        when(transferenciaService.getTransferenciasPorOperador(nomeOperador)).thenReturn(transferencias);

        List<Transferencia> result = transferenciaController.getByOperador(nomeOperador);

        assertEquals(transferencias, result);
    }


    @Test
    void addTransferencia_DeveChamarMetodoAddTransferenciaDoService() {
        Transferencia transferencia = new Transferencia();

        transferenciaController.addTransferencia(transferencia);

        verify(transferenciaService, times(1)).addTransferencia(transferencia);
    }
}