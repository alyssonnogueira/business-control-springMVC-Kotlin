package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ContaAtualizadaDTO
import com.globo.businesscontrol.crosscutting.ContaDTO
import com.globo.businesscontrol.crosscutting.ResponsavelAtualizadoDTO
import com.globo.businesscontrol.crosscutting.ResponsavelDTO
import com.globo.businesscontrol.dataprovider.ContaDataProvider
import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dataprovider.TransacaoDataProvider
import com.globo.businesscontrol.dominio.*
import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import com.globo.businesscontrol.infra.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
internal class TransacaoConsultaServiceTest {
    @InjectMocks
    lateinit var transacaoConsultaService: TransacaoConsultaService

    @Mock
    lateinit var transacaoConsultaRepository: ITransacaoConsultaRepository

    @Test
    fun deveObterTransacao() {
        val despesa: Despesa = TransacaoDataProvider.criarDespesa()
        Mockito.`when`(transacaoConsultaRepository.obterTransacao(ArgumentMatchers.anyLong())).thenReturn(despesa)
        val retornoDespesa: Transacao = transacaoConsultaService.obterTransacao(1L)
        assertEquals(despesa, retornoDespesa)
    }

    @Test
    fun deveObterTodasAsTransacoes() {
        val despesa: Despesa = TransacaoDataProvider.criarDespesa()
        val receita: Receita = TransacaoDataProvider.criarReceita()
        val transferencia: Transferencia = TransacaoDataProvider.criarTransferencia()
        val arrayList = mutableListOf(despesa, receita, transferencia)
        Mockito.`when`(transacaoConsultaRepository.obterTodasTransacoes()).thenReturn(arrayList)
        val listaDeTransacoes: List<Transacao> = transacaoConsultaService.obterTodasTransacoes()
        assertEquals(3, listaDeTransacoes.size)
        assertEquals(despesa, listaDeTransacoes[0])
        assertEquals(receita, listaDeTransacoes[1])
        assertEquals(transferencia, listaDeTransacoes[2])
    }

    @Test
    fun AoObterTodasAsTransacoesDeveRetornarArrayVazio() {
        val arrayList = listOf<Transacao>()
        Mockito.`when`(transacaoConsultaRepository.obterTodasTransacoes()).thenReturn(arrayList)
        val listaDeTransacoes: List<Transacao> = transacaoConsultaService.obterTodasTransacoes()
        assertEquals(0, listaDeTransacoes.size)
    }

    @Test
    fun naoDeveObterTransacaoQuandoNaoExistirNoBanco() {
        Mockito.`when`(transacaoConsultaRepository.obterTransacao(1L)).thenThrow(EntityNotFoundException::class.java)
        Assertions.assertThrows(EntityNotFoundException::class.java) {
            transacaoConsultaService.obterTransacao(1L)
        }
    }
}