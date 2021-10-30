package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dataprovider.ContaDataProvider
import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.infra.IContaConsultaRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.ArrayList
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
internal class ContaConsultaServiceTest {
    @InjectMocks
    lateinit var contaConsultaService: ContaConsultaService

    @Mock
    lateinit var contaConsultaRepository: IContaConsultaRepository

    @Test
    fun deveObterConta() {
        val conta: Conta = ContaDataProvider.criarConta()
        Mockito.`when`(contaConsultaRepository.obterConta(ArgumentMatchers.anyLong())).thenReturn(conta)
        val retornoConta: Conta = contaConsultaService.obterConta(1L)
        assertEquals(conta, retornoConta)
    }

    @Test
    fun deveObterTodasAsContas() {
        val conta: Conta = ContaDataProvider.criarConta()
        val arrayList = mutableListOf(conta)
        Mockito.`when`(contaConsultaRepository.obterTodasAsContas()).thenReturn(arrayList)
        val listaDeContas: List<Conta> = contaConsultaService.obterTodasAsContas()
        assertEquals(1, listaDeContas.size)
        assertEquals(conta, listaDeContas[0])
    }

    @Test
    fun AoObterTodasAsContasDeveRetornarArrayVazio() {
        val arrayList = mutableListOf<Conta>()
        Mockito.`when`(contaConsultaRepository.obterTodasAsContas()).thenReturn(arrayList)
        val listaDeContas: List<Conta> = contaConsultaService.obterTodasAsContas()
        assertEquals(0, listaDeContas.size)
    }

    @Test
    fun naoDeveObterContaQuandoNaoExistirNoBanco() {
        Mockito.`when`(contaConsultaRepository.obterConta(1L)).thenThrow(EntityNotFoundException::class.java)
        Assertions.assertThrows(EntityNotFoundException::class.java) {
            contaConsultaService.obterConta(1L)
        }
    }
}