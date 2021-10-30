package com.globo.businesscontrol.web

import com.globo.businesscontrol.crosscutting.ResponsavelAtualizadoDTO
import com.globo.businesscontrol.crosscutting.ResponsavelDTO
import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.lang.Exception

class ResponsavelControllerIT : BaseControllerIT() {
    @Autowired
    lateinit var responsavelConsultaRepository: IResponsavelConsultaRepository

    @Test
    @Sql(value = [REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Throws(
        Exception::class
    )
    fun deveCriarUmResponsavel() {
        val responsavelDTO = ResponsavelDTO("Alysson")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/v1/responsaveis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(responsavelDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
        val responsaveis: List<Responsavel> = responsavelConsultaRepository.obterTodosOsResponsaveis()
        assertEquals(1, responsaveis.size)
        assertEquals("Alysson", responsaveis[0].nome)
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveAtualizarUmResponsavel() {
        val responsavelDTO = ResponsavelAtualizadoDTO(1L, "José")
        mvc!!.perform(
            MockMvcRequestBuilders.put("/v1/responsaveis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(responsavelDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent())
        val responsaveis: Responsavel = responsavelConsultaRepository.obterResponsavel(1L)
        assertEquals("José", responsaveis.nome)
    }

    @Test
    @SqlGroup(
        Sql(value = [INSERIR_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = [REMOVER_RESPONSAVEIS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    @Throws(
        Exception::class
    )
    fun deveExcluirUmResponsavel() {
        mvc!!.perform(
            MockMvcRequestBuilders.delete("/v1/responsaveis/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent())
        val responsaveis: List<Responsavel> = responsavelConsultaRepository.obterTodosOsResponsaveis()
        assertEquals(0, responsaveis.size)
    }

    companion object {
        private const val INSERIR_RESPONSAVEIS = "../sql/inserirResponsaveis.sql"
        private const val REMOVER_RESPONSAVEIS = "../sql/removerResponsaveis.sql"
    }
}