package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal
import java.time.LocalDateTime

class ContaTest {
    @Test
    fun deveCriarUmaConta() {
        val nomeResponsavel = "Responsavel"
        val responsavel: Responsavel = Mockito.mock(Responsavel::class.java)
        Mockito.`when`(responsavel.nome).thenReturn(nomeResponsavel)
        val nomeConta = "Banco Digital"
        val conta = Conta(nomeConta, BigDecimal.TEN, TipoContaEnum.DEBITO, responsavel)
        Assertions.assertEquals(nomeConta, conta.nome)
        Assertions.assertNotNull(conta.dataCriacao)
        Assertions.assertNotNull(conta.dataAtualizacao)
        Assertions.assertNull(conta.dataExclusao)
        Assertions.assertEquals(nomeResponsavel, conta.responsavel.nome)
        Assertions.assertEquals(BigDecimal.TEN, conta.saldoInicial)
        Assertions.assertEquals(BigDecimal.TEN, conta.saldo)
    }

    @Test
    fun deveAtualizarNomeDaConta() {
        val nomeConta = "Fintech"
        val novoNomeDaConta = "Banco Digital"
        val responsavel: Responsavel = Mockito.mock(Responsavel::class.java)
        Mockito.`when`(responsavel.nome).thenReturn(novoNomeDaConta)
        val conta = Conta(nomeConta, BigDecimal.valueOf(0), TipoContaEnum.DEBITO, responsavel)
        val antigaDataAtualizacao: LocalDateTime = responsavel.dataAtualizacao
        conta.nome = novoNomeDaConta
        Assertions.assertEquals(conta.nome, novoNomeDaConta)
        Assertions.assertNotEquals(conta.dataAtualizacao, antigaDataAtualizacao)
    }

    @Test
    fun deveDebitarSaldoDaConta() {
        val nomeConta = "Banco Digital"
        val saldoOriginal: BigDecimal = BigDecimal.valueOf(1000)
        val responsavel: Responsavel = Mockito.mock(Responsavel::class.java)
        val conta = Conta(nomeConta, saldoOriginal, TipoContaEnum.DEBITO, responsavel)
        val valorDespesa: BigDecimal = BigDecimal.valueOf(500)
        conta.debitarSaldo(valorDespesa)
        Assertions.assertEquals(BigDecimal.valueOf(500), conta.saldo)
        Assertions.assertEquals(saldoOriginal, conta.saldoInicial)
    }

    @Test
    fun deveCreditarSaldoDaConta() {
        val nomeConta = "Banco Digital"
        val saldoOriginal: BigDecimal = BigDecimal.valueOf(1000)
        val responsavel: Responsavel = Mockito.mock(Responsavel::class.java)
        val conta = Conta(nomeConta, saldoOriginal, TipoContaEnum.DEBITO, responsavel)
        val valorCredito: BigDecimal = BigDecimal.valueOf(500)
        conta.creditarSaldo(valorCredito)
        Assertions.assertEquals(BigDecimal.valueOf(1500), conta.saldo)
        Assertions.assertEquals(saldoOriginal, conta.saldoInicial)
    }
}