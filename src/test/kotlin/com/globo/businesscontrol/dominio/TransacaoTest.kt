package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dataprovider.ContaDataProvider
import com.globo.businesscontrol.dataprovider.ResponsavelDataProvider
import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class TransacaoTest {
    private val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
    private val conta: Conta = ContaDataProvider.criarConta()
    private val dataTransacao = Date()
    private val valorTransacao: BigDecimal = BigDecimal.valueOf(100)
    @Test
    fun deveCriarTransacao() {
        val descricaoDespesa = "Comida"
        val categoriaDespesa: CategoriaDespesaEnum = CategoriaDespesaEnum.ALIMENTACAO
        val transacao: Transacao = Despesa(
            dataTransacao, valorTransacao, descricaoDespesa, responsavel, conta, categoriaDespesa
        )
        assertEquals(conta, transacao.conta)
        assertEquals(responsavel, transacao.responsavel)
        assertEquals(
            LocalDateTime.ofInstant(dataTransacao.toInstant(), ZoneId.of("America/Sao_Paulo")),
            transacao.data
        )
        Assertions.assertNotNull(transacao.dataCriacao)
        Assertions.assertNull(transacao.dataExclusao)
        assertEquals(descricaoDespesa, transacao.descricao)
        assertEquals(valorTransacao, transacao.valor)
    }

    @Test
    fun deveCriarTransacaoDoTipoDespesa() {
        val descricaoDespesa = "Comida"
        val categoriaDespesa: CategoriaDespesaEnum = CategoriaDespesaEnum.ALIMENTACAO
        val despesa = Despesa(
            dataTransacao, valorTransacao, descricaoDespesa, responsavel, conta, categoriaDespesa
        )
        assertEquals(TipoTransacaoEnum.DESPESA, despesa.tipoTransacao)
        assertEquals(CategoriaDespesaEnum.ALIMENTACAO, despesa.categoriaDespesa)
    }

    @Test
    fun deveCriarTransacaoDoTipoReceita() {
        val descricaoReceita = "Salário"
        val categoriaReceita: CategoriaReceitaEnum = CategoriaReceitaEnum.SALARIO
        val receita = Receita(
            dataTransacao, valorTransacao, descricaoReceita, responsavel, conta, categoriaReceita
        )
        assertEquals(TipoTransacaoEnum.RECEITA, receita.tipoTransacao)
        assertEquals(CategoriaReceitaEnum.SALARIO, receita.categoriaReceita)
    }

    @Test
    fun deveCriarTransacaoDoTipoTransferencia() {
        val descricaoTransferencia = "Poupanca"
        val contaDestino: Conta = ContaDataProvider.criarConta("Porquinho")
        val transferencia = Transferencia(
            dataTransacao, valorTransacao, descricaoTransferencia, responsavel, conta, contaDestino
        )
        assertEquals(TipoTransacaoEnum.TRANSFERENCIA, transferencia.tipoTransacao)
        Assertions.assertNotEquals(contaDestino.nome, transferencia.conta.nome)
        Assertions.assertNotEquals(conta.nome, transferencia.contaDestino.nome)
        Assertions.assertEquals(contaDestino.nome, transferencia.contaDestino.nome)
    }
}