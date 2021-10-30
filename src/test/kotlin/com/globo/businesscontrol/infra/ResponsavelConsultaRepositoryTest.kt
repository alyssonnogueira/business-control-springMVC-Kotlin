package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dominio.Responsavel
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
internal class ResponsavelConsultaRepositoryTest {

    @InjectMocks
    lateinit var responsavelConsultaRepository: ResponsavelConsultaRepository

    @Mock
    lateinit var responsavelJpaRepository: ResponsavelConsultaRepository.ResponsavelJpaRepository

    @Test
    fun deveObterResponsavel() {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelJpaRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(responsavel))
        val retornoResponsavel: Responsavel = responsavelConsultaRepository.obterResponsavel(1L)
        assertEquals(responsavel, retornoResponsavel)
    }

    @Test
    fun deveObterTodosOsResponsaveis() {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val arrayList: MutableList<Responsavel> = mutableListOf(responsavel)
        Mockito.`when`(responsavelJpaRepository.findAll()).thenReturn(arrayList)
        val listaDeResponsaveis: List<Responsavel> = responsavelConsultaRepository.obterTodosOsResponsaveis()
        assertEquals(1, listaDeResponsaveis.size)
        assertEquals(responsavel, listaDeResponsaveis[0])
    }

    @Test
    fun AoObterTodosOsResponsaveisDeveRetornarArrayVazio() {
        val arrayList: MutableList<Responsavel> = mutableListOf()
        Mockito.`when`(responsavelJpaRepository.findAll()).thenReturn(arrayList)
        val listaDeResponsaveis: List<Responsavel> = responsavelConsultaRepository.obterTodosOsResponsaveis()
        assertEquals(0, listaDeResponsaveis.size)
    }

    @Test
    fun naoDeveObterResponsavelQuandoNaoExistirNoBanco() {
        Mockito.`when`(responsavelJpaRepository.findById(2L)).thenReturn(Optional.empty())
        Assertions.assertThrows(EntityNotFoundException::class.java) {
            responsavelConsultaRepository.obterResponsavel(1L)
        }
    }
}