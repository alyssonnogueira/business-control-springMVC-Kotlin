package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ResponsavelAtualizadoDTO
import com.globo.businesscontrol.crosscutting.ResponsavelDTO
import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import com.globo.businesscontrol.infra.IResponsavelRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class ResponsavelServiceTest {
    @InjectMocks
    lateinit var responsavelService: ResponsavelService

    @Mock
    lateinit var responsavelConsultaRepository: IResponsavelConsultaRepository

    @Mock
    lateinit var responsavelRepository: IResponsavelRepository

    @Test
    fun deveVerificarChamarFuncaoSalvarAoCriarResponsavel() {
        val responsavelDTO = ResponsavelDTO("Alysson")
        responsavelService.criarResponsavel(responsavelDTO)
        Mockito.verify(responsavelRepository, Mockito.times(1)).salvarResponsavel(any())
    }

    @Test
    fun deveVerificarChamarFuncaoSalvarAoAtualizarResponsavel() {
        val responsavelDTO = ResponsavelAtualizadoDTO(1L, "Alysson")
        val responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(1L))
            .thenReturn(responsavel)
        responsavelService.atualizarResponsavel(responsavelDTO)
        Mockito.verify(responsavelRepository, Mockito.times(1)).salvarResponsavel(any())
    }

    @Test
    fun deveVerificarChamarFuncaoExcluirResponsavel() {
        responsavelService.excluirResponsavel(1L)
        Mockito.verify(responsavelRepository, Mockito.times(1)).excluirResponsavel(1L)
    }
}