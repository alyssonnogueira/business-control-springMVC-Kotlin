package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dataprovider.ContaDataProvider
import com.globo.businesscontrol.dominio.Conta
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
internal class ContaConsultaRepositoryTest {
    @InjectMocks
    lateinit var contaConsultaRepository: ContaConsultaRepository

    @Mock
    lateinit var contaJpaRepository: ContaConsultaRepository.ContaJpaRepository

    @Test
    fun deveObterConta() {
        val conta: Conta = ContaDataProvider.criarConta()
        Mockito.`when`(contaJpaRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(conta))
        val retornConta: Conta = contaConsultaRepository.obterConta(1L)
        assertEquals(conta, retornConta)
    }

    @Test
    fun deveObterTodasAsContas() {
        val conta: Conta = ContaDataProvider.criarConta()
        val arrayList: MutableList<Conta> = mutableListOf(conta)
        Mockito.`when`(contaJpaRepository.findAll()).thenReturn(arrayList)
        val listaDeContas: List<Conta> = contaConsultaRepository.obterTodasAsContas()
        assertEquals(1, listaDeContas.size)
        assertEquals(conta, listaDeContas[0])
    }

    @Test
    fun AoObterTodasAsContasDeveRetornarArrayVazio() {
        val arrayList: MutableList<Conta> = mutableListOf()
        Mockito.`when`(contaJpaRepository.findAll()).thenReturn(arrayList)
        val listaDeContas: List<Conta> = contaConsultaRepository.obterTodasAsContas()
        assertEquals(0, listaDeContas.size)
    }

    @Test
    fun naoDeveObterContaQuandoNaoExistirNoBanco() {
        Mockito.`when`(contaJpaRepository.findById(2L)).thenReturn(Optional.empty())
        Assertions.assertThrows(EntityNotFoundException::class.java) {
            contaConsultaRepository.obterConta(1L)
        }
    }
}