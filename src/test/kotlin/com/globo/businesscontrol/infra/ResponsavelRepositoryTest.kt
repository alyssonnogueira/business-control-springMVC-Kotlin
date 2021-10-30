package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dominio.Responsavel
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class ResponsavelRepositoryTest {
    @InjectMocks
    lateinit var responsavelRepository: ResponsavelRepository

    @Mock
    lateinit var responsavelJpaRepository: ResponsavelRepository.ResponsavelJpaRepository

    @Test
    fun deveSalvarUmResponsavel() {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        responsavelRepository.salvarResponsavel(responsavel)
        Mockito.verify(responsavelJpaRepository, Mockito.times(1)).save(responsavel)
    }

    @Test
    fun deveExcluirUmResponsavel() {
        responsavelRepository.excluirResponsavel(1L)
        Mockito.verify(responsavelJpaRepository, Mockito.times(1)).deleteById(1L)
    }
}