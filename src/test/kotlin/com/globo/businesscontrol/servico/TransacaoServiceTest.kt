package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.*
import com.globo.businesscontrol.dataprovider.ContaDataProvider
import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dataprovider.TransacaoDataProvider
import com.globo.businesscontrol.dominio.*
import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
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
import org.mockito.Mockito.spy
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.util.*
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
internal class TransacaoServiceTest {
    @InjectMocks
    lateinit var transacaoService: TransacaoService

    @Mock
    lateinit var transacaoConsultaRepository: ITransacaoConsultaRepository

    @Mock
    lateinit var transacaoRepository: ITransacaoRepository

    @Mock
    lateinit var responsavelConsultaRepository: IResponsavelConsultaRepository

    @Mock
    lateinit var contaConsultaRepository: IContaConsultaRepository

    @Test
    fun deveVerificarACriacaoDeUmaDespesa() {
        val despesaDTO = DespesaDTO(
            CategoriaDespesaEnum.ALIMENTACAO,
            Date(),
            BigDecimal.TEN,
            "descricao",
            1L,
            1L
        )
        val responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(1L))
            .thenReturn(responsavel)
        val conta = ContaDataProvider.criarConta()
        Mockito.`when`(contaConsultaRepository.obterConta(1L)).thenReturn(conta)
        transacaoService.criarTransferencia(despesaDTO)
        Mockito.verify(transacaoRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }

    @Test
    fun deveVerificarACriacaoDeUmaReceita() {
        val receitaDTO = ReceitaDTO(
            CategoriaReceitaEnum.SALARIO,
            Date(),
            BigDecimal.TEN,
            "descricao",
            1L,
            1L
        )
        val responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(1L))
            .thenReturn(responsavel)
        val conta = ContaDataProvider.criarConta()
        Mockito.`when`(contaConsultaRepository.obterConta(1L)).thenReturn(conta)
        transacaoService.criarTransferencia(receitaDTO)
        Mockito.verify(transacaoRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }

    @Test
    fun deveVerificarACriacaoDeUmaTransferencia() {
        val transferenciaDTO = TransferenciaDTO(
            2L,
            Date(),
            BigDecimal.TEN,
            "descricao",
            1L,
            1L
        )
        val responsavel = ResponsavelDataProvider.criarResponsavel()
        Mockito.`when`(responsavelConsultaRepository.obterResponsavel(1L))
            .thenReturn(responsavel)
        val conta = ContaDataProvider.criarConta()
        Mockito.`when`(contaConsultaRepository.obterConta(1L)).thenReturn(conta)
        val conta2 = ContaDataProvider.criarConta("Conta 2")
        Mockito.`when`(contaConsultaRepository.obterConta(2L))
            .thenReturn(conta2)
        transacaoService.criarTransferencia(transferenciaDTO)
        Mockito.verify(transacaoRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }

    @Test
    fun deveVerificarAExclusaoDeUmaTransacao() {
        val transacao: Transacao = spy(TransacaoDataProvider.criarDespesa())
        Mockito.`when`(transacaoConsultaRepository.obterTransacao(1L)).thenReturn(transacao)
        transacaoService.excluirTransacao(1L)
        Mockito.verify(transacao, Mockito.times(1)).excluirTransacao()
        Mockito.verify(transacaoRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }
}