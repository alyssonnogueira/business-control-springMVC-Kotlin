package com.globo.businesscontrol.dataprovider

import com.globo.businesscontrol.dominio.*
import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
import org.mockito.Mockito
import java.math.BigDecimal
import java.util.*

object TransacaoDataProvider {
    fun criarDespesa(): Despesa {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta: Conta = ContaDataProvider.criarConta()
        val dataTransacao = Date()
        val valorTransacao: BigDecimal = BigDecimal.valueOf(100)
        val descricaoDespesa = "Comida"
        val categoriaDespesa: CategoriaDespesaEnum = CategoriaDespesaEnum.ALIMENTACAO
        val despesa = Mockito.spy(Despesa(
            dataTransacao, valorTransacao, descricaoDespesa, responsavel, conta, categoriaDespesa
        ))
        Mockito.`when`(despesa.id).thenReturn(1L)
        return despesa
    }

    fun criarReceita(): Receita {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta: Conta = ContaDataProvider.criarConta()
        val dataTransacao = Date()
        val valorTransacao: BigDecimal = BigDecimal.valueOf(100)
        val descricaoReceita = "Salário"
        val categoriaReceita: CategoriaReceitaEnum = CategoriaReceitaEnum.SALARIO
        val receita = Mockito.spy(Receita(
            dataTransacao, valorTransacao, descricaoReceita, responsavel, conta, categoriaReceita
        ))
        Mockito.`when`(receita.id).thenReturn(1L)
        return receita
    }

    fun criarTransferencia(): Transferencia {
        val responsavel: Responsavel = ResponsavelDataProvider.criarResponsavel()
        val conta: Conta = ContaDataProvider.criarConta()
        val dataTransacao = Date()
        val valorTransacao: BigDecimal = BigDecimal.valueOf(100)
        val descricaoTransferencia = "Poupanca"
        val contaDestino: Conta = ContaDataProvider.criarConta("Porquinho")
        val transferencia = Mockito.spy(Transferencia(
            dataTransacao, valorTransacao, descricaoTransferencia, responsavel, conta, contaDestino
        ))
        Mockito.`when`(transferencia.id).thenReturn(1L)
        return transferencia
    }
}