package com.globo.businesscontrol.web

import com.globo.businesscontrol.crosscutting.ContaAtualizadaDTO
import com.globo.businesscontrol.crosscutting.ContaDTO
import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import com.globo.businesscontrol.infra.IContaConsultaRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.lang.Exception
import java.math.BigDecimal

class ContaControllerIT : BaseControllerIT() {

    @Autowired
    lateinit var contaConsultaRepository: IContaConsultaRepository

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_CONTAS, REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveCriarConta() {
        val nomeBanco = "Banco Digital"
        val contaDTO = ContaDTO(nomeBanco, BigDecimal.TEN, TipoContaEnum.DEBITO, 1L)
        mvc!!.perform(
            MockMvcRequestBuilders.post("/v1/contas/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(contaDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
        val contas: List<Conta> = contaConsultaRepository.obterTodasAsContas()
        assertEquals(1, contas.size)
        assertEquals(nomeBanco, contas[0].nome)
        Assertions.assertTrue(BigDecimal.TEN.compareTo(contas[0].saldoInicial) == 0)
        Assertions.assertTrue(BigDecimal.TEN.compareTo(contas[0].saldo) == 0)
        assertEquals(TipoContaEnum.DEBITO, contas[0].tipoConta)
        Assertions.assertNotNull(contas[0].responsavel)
        assertEquals(1L, contas[0].responsavel.id)
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_CONTAS, REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveAtualizarConta() {
        val nomeAtualizadoBanco = "Banco Digital 2"
        val contaDTO = ContaAtualizadaDTO(1L, nomeAtualizadoBanco)
        mvc!!.perform(
            MockMvcRequestBuilders.put("/v1/contas/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(contaDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent())
        val conta: Conta = contaConsultaRepository.obterConta(1L)
        assertEquals(nomeAtualizadoBanco, conta.nome)
        Assertions.assertTrue(BigDecimal.valueOf(100).compareTo(conta.saldoInicial) == 0)
        Assertions.assertTrue(BigDecimal.valueOf(100).compareTo(conta.saldo) == 0)
        assertEquals(TipoContaEnum.DEBITO, conta.tipoConta)
        Assertions.assertNotNull(conta.responsavel)
        assertEquals(1L, conta.responsavel.id)
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_CONTAS, REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveExcluirConta() {
        mvc!!.perform(
            MockMvcRequestBuilders.delete("/v1/contas/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent())
        val contas: List<Conta> = contaConsultaRepository.obterTodasAsContas()
        assertEquals(1, contas.size)
    }

    companion object {
        private const val INSERIR_RESPONSAVEIS = "../sql/inserirResponsaveis.sql"
        private const val REMOVER_RESPONSAVEIS = "../sql/removerResponsaveis.sql"
        private const val INSERIR_CONTAS = "../sql/inserirContas.sql"
        private const val REMOVER_CONTAS = "../sql/removerContas.sql"
    }
}