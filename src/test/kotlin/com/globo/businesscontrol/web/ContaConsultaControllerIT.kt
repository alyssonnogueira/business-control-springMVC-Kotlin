package com.globo.businesscontrol.web

import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.lang.Exception

class ContaConsultaControllerIT : BaseControllerIT() {

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_CONTAS, REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveListarTodasAsContas() {
        mvc!!.perform(
            MockMvcRequestBuilders.get("/v1/contas/")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath<Collection<*>>("$.[*]", Matchers.hasSize<Any>(2)))
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].nome",
                    containsInAnyOrder("Conta Digital", "Conta Corrente")
                )
            )
            .andExpect(
                jsonPath<Iterable<Double>>(
                    "$.[*].saldoInicial",
                    containsInAnyOrder(100.0, 100.0)
                )
            )
            .andExpect(
                jsonPath<Iterable<Double>>(
                    "$.[*].saldo",
                    containsInAnyOrder(100.0, 100.0)
                )
            )
            .andExpect(
                jsonPath(
                    "$.[*].tipoConta",
                    containsInAnyOrder(TipoContaEnum.DEBITO.name, TipoContaEnum.DEBITO.name)
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].dataCriacao",
                    containsInAnyOrder("2020-10-09T01:41:51", "2020-10-09T01:41:51")
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].dataAtualizacao",
                    containsInAnyOrder("2020-10-09T01:41:51", "2020-10-09T01:41:51")
                )
            )
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_CONTAS, REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveBuscarUmaConta() {
        mvc!!.perform(
            MockMvcRequestBuilders.get("/v1/contas/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id", Matchers.`is`(1)))
            .andExpect(jsonPath("$.nome", Matchers.`is`("Conta Digital")))
            .andExpect(jsonPath("$.saldoInicial", Matchers.`is`(100.0)))
            .andExpect(jsonPath("$.saldo", Matchers.`is`(100.0)))
            .andExpect(jsonPath("$.tipoConta", Matchers.`is`(TipoContaEnum.DEBITO.name)))
            .andExpect(jsonPath("$.dataCriacao", Matchers.`is`("2020-10-09T01:41:51")))
            .andExpect(
                jsonPath(
                    "$.dataAtualizacao",
                    Matchers.`is`("2020-10-09T01:41:51")
                )
            )
    }

    companion object {
        private const val INSERIR_RESPONSAVEIS = "../sql/inserirResponsaveis.sql"
        private const val REMOVER_RESPONSAVEIS = "../sql/removerResponsaveis.sql"
        private const val INSERIR_CONTAS = "../sql/inserirContas.sql"
        private const val REMOVER_CONTAS = "../sql/removerContas.sql"
    }
}