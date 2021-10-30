package com.globo.businesscontrol.web

import com.globo.businesscontrol.crosscutting.DespesaDTO
import com.globo.businesscontrol.crosscutting.ReceitaDTO
import com.globo.businesscontrol.crosscutting.TransferenciaDTO
import com.globo.businesscontrol.dominio.*
import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import com.globo.businesscontrol.infra.IContaConsultaRepository
import com.globo.businesscontrol.infra.ITransacaoConsultaRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.lang.Exception
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

internal class TransacaoControllerIT : BaseControllerIT() {
    @Autowired
    lateinit var transacaoConsultaRepository: ITransacaoConsultaRepository

    @Autowired
    lateinit var contaConsultaRepository: IContaConsultaRepository

    var data = Date()
    var valor: BigDecimal = BigDecimal.valueOf(100)
    var idResponsavel = 1L
    var idConta = 1L

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(
            value = [REMOVER_TRANSACOES, REMOVER_CONTAS, REMOVER_RESPONSAVEIS],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    @Throws(
        Exception::class
    )
    fun deveCriarDespesa() {
        val descricao = "Supermercado X"
        val despesaDTO = DespesaDTO(CategoriaDespesaEnum.MERCADO, data, valor, descricao, idResponsavel, idConta)
        mvc!!.perform(
            MockMvcRequestBuilders.post("/v1/transacoes/despesa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(despesaDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
        val transacoes: List<Transacao> = transacaoConsultaRepository.obterTodasTransacoes()
        assertEquals(1, transacoes.size)
        assertEquals(LocalDateTime.ofInstant(data.toInstant(), ZoneId.of("America/Sao_Paulo")), transacoes[0].data)
        Assertions.assertTrue(valor.compareTo(transacoes[0].valor) == 0)
        assertEquals(descricao, transacoes[0].descricao)
        assertEquals(idResponsavel, transacoes[0].responsavel.id)
        assertEquals(idConta, transacoes[0].conta.id)
        assertEquals(TipoTransacaoEnum.DESPESA, transacoes[0].tipoTransacao)
        Assertions.assertTrue(transacoes[0] is Despesa)
        val despesa: Despesa = transacoes[0] as Despesa
        assertEquals(CategoriaDespesaEnum.MERCADO, despesa.categoriaDespesa)
        assertNotEquals(despesa.conta.saldoInicial, despesa.conta.saldo)
        assertEquals(BigDecimal.ZERO.toDouble(), despesa.conta.saldo.toDouble())
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(
            value = [REMOVER_TRANSACOES, REMOVER_CONTAS, REMOVER_RESPONSAVEIS],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    @Throws(
        Exception::class
    )
    fun deveCriarReceita() {
        val descricao = "Salario"
        val receitaDTO = ReceitaDTO(CategoriaReceitaEnum.SALARIO, data, valor, descricao, idResponsavel, idConta)
        mvc!!.perform(
            MockMvcRequestBuilders.post("/v1/transacoes/receita")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(receitaDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
        val transacoes: List<Transacao> = transacaoConsultaRepository.obterTodasTransacoes()
        assertEquals(1, transacoes.size)
        assertEquals(LocalDateTime.ofInstant(data.toInstant(), ZoneId.of("America/Sao_Paulo")), transacoes[0].data)
        assertEquals(valor.toDouble(), transacoes[0].valor.toDouble())
        assertEquals(descricao, transacoes[0].descricao)
        assertEquals(idResponsavel, transacoes[0].responsavel.id)
        assertEquals(idConta, transacoes[0].conta.id)
        assertEquals(TipoTransacaoEnum.RECEITA, transacoes[0].tipoTransacao)
        Assertions.assertTrue(transacoes[0] is Receita)
        val receita: Receita = transacoes[0] as Receita
        assertEquals(CategoriaReceitaEnum.SALARIO, receita.categoriaReceita)
        assertNotEquals(receita.conta.saldoInicial, receita.conta.saldo)
        assertEquals(BigDecimal.valueOf(200).toDouble(), receita.conta.saldo.toDouble())
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(
            value = [REMOVER_TRANSACOES, REMOVER_CONTAS, REMOVER_RESPONSAVEIS],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    @Throws(
        Exception::class
    )
    fun deveCriarTransferenciaEntreContas() {
        val descricao = "TED"
        val idConta2 = 2L
        val transferenciaDTO = TransferenciaDTO(idConta2, data, valor, descricao, idResponsavel, idConta)
        mvc!!.perform(
            MockMvcRequestBuilders.post("/v1/transacoes/transferencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(transferenciaDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
        val transacoes: List<Transacao> = transacaoConsultaRepository.obterTodasTransacoes()
        assertEquals(1, transacoes.size)
        assertEquals(LocalDateTime.ofInstant(data.toInstant(), ZoneId.of("America/Sao_Paulo")), transacoes[0].data)
        assertEquals(valor.toDouble(), transacoes[0].valor.toDouble())
        assertEquals(descricao, transacoes[0].descricao)
        assertEquals(idResponsavel, transacoes[0].responsavel.id)
        assertEquals(idConta, transacoes[0].conta.id)
        assertEquals(TipoTransacaoEnum.TRANSFERENCIA, transacoes[0].tipoTransacao)
        Assertions.assertTrue(transacoes[0] is Transferencia)
        val transferencia: Transferencia = transacoes[0] as Transferencia
        assertNotEquals(transferencia.conta.saldoInicial, transferencia.conta.saldo)
        assertEquals(BigDecimal.valueOf(0).toDouble(), transferencia.conta.saldo.toDouble())
        assertNotEquals(transferencia.contaDestino.saldoInicial, transferencia.contaDestino.saldo)
        assertEquals(BigDecimal.valueOf(200).toDouble(), transferencia.contaDestino.saldo.toDouble())
    }

    @Test
    @SqlGroup(
        Sql(
            value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS, INSERIR_TRANSACOES],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ), Sql(
            value = [REMOVER_TRANSACOES, REMOVER_CONTAS, REMOVER_RESPONSAVEIS],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    @Throws(
        Exception::class
    )
    fun deveExcluirTransferencia() {
        mvc!!.perform(
            MockMvcRequestBuilders.delete("/v1/transacoes/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
        val transacoes: List<Transacao> = transacaoConsultaRepository.obterTodasTransacoes()
        assertEquals(2, transacoes.size)
        val conta: Conta = contaConsultaRepository.obterConta(1L)
        assertNotEquals(conta.saldoInicial.toDouble(), conta.saldo.toDouble())
        assertEquals(BigDecimal.valueOf(200).toDouble(), conta.saldo.toDouble())
    }

    companion object {
        private const val INSERIR_RESPONSAVEIS = "../sql/inserirResponsaveis.sql"
        private const val REMOVER_RESPONSAVEIS = "../sql/removerResponsaveis.sql"
        private const val INSERIR_CONTAS = "../sql/inserirContas.sql"
        private const val REMOVER_CONTAS = "../sql/removerContas.sql"
        private const val INSERIR_TRANSACOES = "../sql/inserirTransacoes.sql"
        private const val REMOVER_TRANSACOES = "../sql/removerTransacoes.sql"
    }
}