package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
internal class ResponsavelConsultaServiceTest {
    @InjectMocks
    lateinit var responsavelConsultaService: ResponsavelConsultaService

    @Mock
    lateinit var responsavelConsultaRepository: IResponsavelConsultaRepository
    @Test
    fun deveObterResponsavel() {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(ArgumentMatchers.anyLong()))
            .thenReturn(responsavel)
        val retornoResponsavel: Responsavel = responsavelConsultaService.obterResponsavel(1L)
        assertEquals(responsavel, retornoResponsavel)
    }

    @Test
    fun deveObterTodosOsResponsaveis() {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val arrayList = mutableListOf(responsavel)
        Mockito.`when`(responsavelConsultaRepository.obterTodosOsResponsaveis()).thenReturn(arrayList)
        val listaDeResponsaveis: List<Responsavel> = responsavelConsultaService.obterTodosOsResponsaveis()
        assertEquals(1, listaDeResponsaveis.size)
        assertEquals(responsavel, listaDeResponsaveis[0])
    }

    @Test
    fun AoObterTodosOsResponsaveisDeveRetornarArrayVazio() {
        val arrayList = listOf<Responsavel>()
        Mockito.`when`(responsavelConsultaRepository.obterTodosOsResponsaveis()).thenReturn(arrayList)
        val listaDeResponsaveis: List<Responsavel> = responsavelConsultaService.obterTodosOsResponsaveis()
        assertEquals(0, listaDeResponsaveis.size)
    }

    @Test
    fun naoDeveObterResponsavelQuandoNaoExistirNoBanco() {
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(1L)).thenThrow(EntityNotFoundException::class.java)
        Assertions.assertThrows(EntityNotFoundException::class.java) {
            responsavelConsultaService.obterResponsavel(1L)
        }
    }
}