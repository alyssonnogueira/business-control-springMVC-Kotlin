package com.globo.businesscontrol.dataprovider

import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever
import java.math.BigDecimal

object ContaDataProvider {
    fun criarConta(): Conta {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta = Mockito.spy(Conta("Banco Digital", BigDecimal.TEN, TipoContaEnum.DEBITO, responsavel))
        whenever(conta.id).thenReturn(1L)
        return conta
    }

    fun criarConta(nome: String): Conta {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta = Mockito.spy(Conta(nome, BigDecimal.TEN, TipoContaEnum.DEBITO, responsavel))
        whenever(conta.id).thenReturn(1L)
        return conta
    }

    fun criarConta(responsavel: Responsavel): Conta {
        val conta = Mockito.spy(Conta("Banco Digital", BigDecimal.TEN, TipoContaEnum.DEBITO, responsavel))
        whenever(conta.id).thenReturn(1L)
        return conta
    }

    fun criarContaDebito(): Conta {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta = Mockito.spy(Conta("Banco Digital", BigDecimal.TEN, TipoContaEnum.DEBITO, responsavel))
        whenever(conta.id).thenReturn(1L)
        return conta
    }

    fun criarContaCredito(): Conta {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta = Mockito.spy(Conta("Banco Digital", BigDecimal.TEN, TipoContaEnum.CREDITO, responsavel))
        whenever(conta.id).thenReturn(1L)
        return conta
    }
}