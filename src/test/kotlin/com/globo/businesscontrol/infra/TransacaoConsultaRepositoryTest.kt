package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dataprovider.TransacaoDataProvider
import com.globo.businesscontrol.dominio.Despesa
import com.globo.businesscontrol.dominio.Receita
import com.globo.businesscontrol.dominio.Transacao
import com.globo.businesscontrol.dominio.Transferencia
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
internal class TransacaoConsultaRepositoryTest {
    @InjectMocks
    lateinit var transacaoConsultaRepository: TransacaoConsultaRepository

    @Mock
    lateinit var transacaoJpaRepository: TransacaoConsultaRepository.TransacaoJpaRepository
    @Test
    fun deveObterTransacao() {
        val despesa: Despesa = TransacaoDataProvider.criarDespesa()
        Mockito.`when`(transacaoJpaRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(despesa))
        val retornoDespesa: Transacao = transacaoConsultaRepository.obterTransacao(1L)
        assertEquals(despesa, retornoDespesa)
    }

    @Test
    fun deveObterTodasAsTransacoes() {
        val despesa: Despesa = TransacaoDataProvider.criarDespesa()
        val receita: Receita = TransacaoDataProvider.criarReceita()
        val transferencia: Transferencia = TransacaoDataProvider.criarTransferencia()
        val arrayList = mutableListOf(despesa, receita, transferencia)
        Mockito.`when`(transacaoJpaRepository.findAll()).thenReturn(arrayList)
        val listaDeTransacoes: List<Transacao> = transacaoConsultaRepository!!.obterTodasTransacoes()
        assertEquals(3, listaDeTransacoes.size)
        assertEquals(despesa, listaDeTransacoes[0])
        assertEquals(receita, listaDeTransacoes[1])
        assertEquals(transferencia, listaDeTransacoes[2])
    }

    @Test
    fun aoObterTodasAsTransacoesDeveRetornarArrayVazio() {
        val arrayList = mutableListOf<Transacao>()
        Mockito.`when`(transacaoJpaRepository.findAll()).thenReturn(arrayList)
        val listaDeTransacoes: List<Transacao> = transacaoConsultaRepository.obterTodasTransacoes()
        assertEquals(0, listaDeTransacoes.size)
    }

    @Test
    fun naoDeveObterTransacaoQuandoNaoExistirNoBanco() {
        Mockito.`when`(transacaoJpaRepository.findById(1L)).thenReturn(Optional.empty())
        Assertions.assertThrows(EntityNotFoundException::class.java) {
            transacaoConsultaRepository.obterTransacao(1L)
        }
    }
}