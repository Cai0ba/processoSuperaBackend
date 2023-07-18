package br.com.banco.transfer;

import br.com.banco.account.Conta;
import br.com.banco.account.ContaRepository;
import br.com.banco.transfer.domain.Transferencia;
import br.com.banco.transfer.domain.TransferenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    private TransferenciaService transferenciaService;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Mock
    private ContaRepository contaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferenciaService = new TransferenciaService(transferenciaRepository, contaRepository);
    }

    @Test
    void getTransferenciasPorConta_DeveRetornarTransferenciasAssociadasAConta() {
        Long idConta = 1L;
        Conta conta = new Conta();
        conta.setId(idConta);
        Transferencia transferencia1 = new Transferencia();
        transferencia1.setConta(conta);
        Transferencia transferencia2 = new Transferencia();
        transferencia2.setConta(conta);
        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        when(contaRepository.findById(idConta)).thenReturn(Optional.of(conta));
        when(transferenciaRepository.findAll()).thenReturn(transferencias);

        List<Transferencia> result = transferenciaService.getTransferenciasPorConta(idConta);
        assertEquals(transferencias, result);
    }

    @Test
    void getTransferenciasPorConta_DeveLancarIllegalArgumentExceptionQuandoNaoExistirConta() {
        Long idConta = 1L;
        when(contaRepository.findById(idConta)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.getTransferenciasPorConta(idConta);
        });
    }

    @Test
    void addTransferencia_DeveSalvarTransferenciaNoRepositorio() {
        Transferencia transferencia = new Transferencia();

        transferenciaService.addTransferencia(transferencia);

        verify(transferenciaRepository, times(1)).save(transferencia);
    }
}
