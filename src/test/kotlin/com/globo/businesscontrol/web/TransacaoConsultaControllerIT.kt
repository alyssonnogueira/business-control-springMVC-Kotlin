package com.globo.businesscontrol.web

import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
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

class TransacaoConsultaControllerIT : BaseControllerIT() {
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
    fun deveListarTodasAsTransacoes() {
        mvc!!.perform(MockMvcRequestBuilders.get("/v1/transacoes/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath<Collection<*>>("$.[*]", Matchers.hasSize<Any>(3)))
            .andExpect(jsonPath<Iterable<Int>>("$.[*].id", Matchers.containsInAnyOrder(1, 2, 3)))
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].data",
                    containsInAnyOrder(
                        "2020-11-09T10:15:00", "2020-11-09T10:15:00", "2020-11-09T10:15:00"
                    )
                )
            )
            .andExpect(
                jsonPath<Iterable<Double>>(
                    "$.[*].valor",
                    containsInAnyOrder(100.0, 100.0, 100.0)
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].descricao",
                    containsInAnyOrder("Comprei algo", "Salario", "Poupanca")
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].responsavel.nome",
                    containsInAnyOrder("Alysson", "Alysson", "Alysson")
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].conta.nome",
                    containsInAnyOrder("Conta Digital", "Conta Digital", "Conta Digital")
                )
            )
            .andExpect(
                jsonPath(
                    "$.[*].tipoTransacao",
                    containsInAnyOrder(
                        TipoTransacaoEnum.DESPESA.name,
                        TipoTransacaoEnum.RECEITA.name,
                        TipoTransacaoEnum.TRANSFERENCIA.name
                    )
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.[*].dataCriacao",
                    containsInAnyOrder(
                        "2020-11-09T10:30:00", "2020-11-09T10:30:00", "2020-11-09T10:30:00"
                    )
                )
            )
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
    fun deveListarTodasAsTransacoesComFiltro() {
        mvc!!.perform(
            MockMvcRequestBuilders.get("/v1/transacoes/paginado")
                .param("descricaoCategoria", "a")
                .param("idsResponsaveis", "1", "2")
                .param("idsContas", "1", "2")
                .param("tiposTransacao", "DESPESA")
                .param("dataInicial", "2020-01-30")
                .param("dataFinal", "2020-11-30")
                .param("dataFinal", "2020-11-30")
                .param("pagina", "0")
                .param("limite", "3")
                .param("ordem", "DESC")
                .param("colunaDeOrdenacao", "data")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath<Collection<*>>("$.content.[*]", Matchers.hasSize<Any>(1)))
            .andExpect(
                jsonPath<Iterable<Int>>(
                    "$.content.[*].id",
                    containsInAnyOrder(1)
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.content.[*].data",
                    containsInAnyOrder("2020-11-09T10:15:00")
                )
            )
            .andExpect(
                jsonPath<Iterable<Double>>(
                    "$.content.[*].valor",
                    containsInAnyOrder(100.0)
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.content.[*].descricao",
                    containsInAnyOrder("Comprei algo")
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.content.[*].responsavel.nome",
                    containsInAnyOrder("Alysson")
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.content.[*].conta.nome",
                    Matchers.containsInAnyOrder("Conta Digital")
                )
            )
            .andExpect(
                jsonPath(
                    "$.content.[*].tipoTransacao",
                    containsInAnyOrder(TipoTransacaoEnum.DESPESA.name)
                )
            )
            .andExpect(
                jsonPath<Iterable<String>>(
                    "$.content.[*].dataCriacao",
                    containsInAnyOrder("2020-11-09T10:30:00")
                )
            )
    }

    @Test
    @SqlGroup(
        Sql(
            value = [INSERIR_RESPONSAVEIS, INSERIR_CONTAS, INSERIR_TRANSACOES],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            value = [REMOVER_TRANSACOES, REMOVER_CONTAS, REMOVER_RESPONSAVEIS],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    @Throws(
        Exception::class
    )
    fun deveBuscarUmaTransacaoComTipoDebito() {
        mvc!!.perform(MockMvcRequestBuilders.get("/v1/transacoes/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id", Matchers.`is`(1)))
            .andExpect(jsonPath("$.data", Matchers.`is`("2020-11-09T10:15:00")))
            .andExpect(jsonPath("$.valor", Matchers.`is`(100.0)))
            .andExpect(jsonPath("$.descricao", Matchers.`is`("Comprei algo")))
            .andExpect(jsonPath("$.responsavel.nome", Matchers.`is`("Alysson")))
            .andExpect(jsonPath("$.conta.nome", Matchers.`is`("Conta Digital")))
            .andExpect(jsonPath("$.tipoTransacao", Matchers.`is`(TipoTransacaoEnum.DESPESA.name)))
            .andExpect(jsonPath("$.dataCriacao", Matchers.`is`("2020-11-09T10:30:00")))
            .andExpect(
                jsonPath("$.categoriaDespesa", Matchers.`is`(CategoriaDespesaEnum.ALIMENTACAO.name))
            )
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
    fun deveBuscarUmaTransacaoComTipoReceita() {
        mvc!!.perform(MockMvcRequestBuilders.get("/v1/transacoes/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id", Matchers.`is`(2)))
            .andExpect(jsonPath("$.data", Matchers.`is`("2020-11-09T10:15:00")))
            .andExpect(jsonPath("$.valor", Matchers.`is`(100.0)))
            .andExpect(jsonPath("$.descricao", Matchers.`is`("Salario")))
            .andExpect(jsonPath("$.responsavel.nome", Matchers.`is`("Alysson")))
            .andExpect(jsonPath("$.conta.nome", Matchers.`is`("Conta Digital")))
            .andExpect(jsonPath("$.tipoTransacao", Matchers.`is`(TipoTransacaoEnum.RECEITA.name)))
            .andExpect(jsonPath("$.dataCriacao", Matchers.`is`("2020-11-09T10:30:00")))
            .andExpect(
                jsonPath("$.categoriaReceita", Matchers.`is`(CategoriaReceitaEnum.SALARIO.name))
            )
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
    fun deveBuscarUmaTransacaoComTipoTransferencia() {
        mvc!!.perform(MockMvcRequestBuilders.get("/v1/transacoes/3").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id", Matchers.`is`(3)))
            .andExpect(jsonPath("$.data", Matchers.`is`("2020-11-09T10:15:00")))
            .andExpect(jsonPath("$.valor", Matchers.`is`(100.0)))
            .andExpect(jsonPath("$.descricao", Matchers.`is`("Poupanca")))
            .andExpect(jsonPath("$.responsavel.nome", Matchers.`is`("Alysson")))
            .andExpect(jsonPath("$.conta.nome", Matchers.`is`("Conta Digital")))
            .andExpect(jsonPath("$.tipoTransacao", Matchers.`is`(TipoTransacaoEnum.TRANSFERENCIA.name)))
            .andExpect(jsonPath("$.dataCriacao", Matchers.`is`("2020-11-09T10:30:00")))
            .andExpect(jsonPath("$.contaDestino.nome", Matchers.`is`("Conta Corrente")))
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