package com.globo.businesscontrol.web

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class ResponsavelConsultaControllerIT : BaseControllerIT() {
    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveListarTodosOsResponsaveis() {
        mvc!!.perform(
            MockMvcRequestBuilders.get("/v1/responsaveis/")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasSize<Any>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].nome", Matchers.containsInAnyOrder("Alysson")))
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveBuscarUmResponsavel() {
        mvc!!.perform(
            MockMvcRequestBuilders.get("/v1/responsaveis/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome", Matchers.`is`("Alysson")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dataCriacao", Matchers.`is`("2020-10-09T01:41:51")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dataAtualizacao", Matchers.`is`("2020-10-09T01:41:51")))
    }

    companion object {
        private const val INSERIR_RESPONSAVEIS = "../sql/inserirResponsaveis.sql"
        private const val REMOVER_RESPONSAVEIS = "../sql/removerResponsaveis.sql"
    }
}