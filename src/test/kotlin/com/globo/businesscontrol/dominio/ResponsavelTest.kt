package com.globo.businesscontrol.dominio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class ResponsavelTest {
    @Test
    fun deveCriarUmResponsavel() {
        val nome = "João"
        val responsavel = Responsavel(nome)
        Assertions.assertEquals(responsavel.nome, nome)
        Assertions.assertNotNull(responsavel.dataCriacao)
        Assertions.assertNotNull(responsavel.dataAtualizacao)
        Assertions.assertNull(responsavel.dataExclusao)
    }

    @Test
    fun deveAtualizarNomeDoResponsavel() {
        val nome = "João"
        val novoNome = "José"
        val responsavel = Responsavel(nome)
        val antigaDataAtualizacao: LocalDateTime = responsavel.dataAtualizacao
        responsavel.nome = novoNome
        Assertions.assertEquals(responsavel.nome, novoNome)
        assertNotEquals(responsavel.dataAtualizacao, antigaDataAtualizacao)
    }
}