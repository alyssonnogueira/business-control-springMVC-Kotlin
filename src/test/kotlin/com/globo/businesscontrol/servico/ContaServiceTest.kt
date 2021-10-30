package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ContaAtualizadaDTO
import com.globo.businesscontrol.crosscutting.ContaDTO
import com.globo.businesscontrol.dataprovider.ContaDataProvider
import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import com.globo.businesscontrol.infra.IContaConsultaRepository
import com.globo.businesscontrol.infra.IContaRepository
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
class ContaServiceTest {
    @InjectMocks
    lateinit var contaService: ContaService

    @Mock
    lateinit var contaRepository: IContaRepository

    @Mock
    lateinit var contaConsultaRepository: IContaConsultaRepository

    @Mock
    lateinit var responsavelConsultaRepository: IResponsavelConsultaRepository

    @BeforeEach
    fun setUp() {
        val responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(1L))
            .thenReturn(responsavel)
    }

    @Test
    fun deveChamarFuncaoSalvarAoCriarConta() {
        val contaDTO = ContaDTO("Nome da Conta", BigDecimal.TEN, TipoContaEnum.DEBITO, 1L)
        contaService.criarConta(contaDTO)
        Mockito.verify(responsavelConsultaRepository, Mockito.times(1)).obterResponsavel(1L)
        Mockito.verify(contaRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }

    @Test
    fun deveChamarFuncaoSalvarAoAtualizarConta() {
        val contaAtualizadaDTO = ContaAtualizadaDTO(1L, "Nome da Conta")
        val conta = ContaDataProvider.criarConta()
        Mockito.`when`(contaConsultaRepository.obterConta(1L)).thenReturn(conta)
        contaService.atualizarConta(contaAtualizadaDTO)
        Mockito.verify(contaConsultaRepository, Mockito.times(1)).obterConta(1L)
        Mockito.verify(contaRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }

    @Test
    fun deveChamarFuncaoDeletarAoExcluirConta() {
        contaService.excluirConta(1L)
        Mockito.verify(contaRepository, Mockito.times(1)).deleteById(1L)
    }
}