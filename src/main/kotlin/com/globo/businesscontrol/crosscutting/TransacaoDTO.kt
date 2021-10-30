package com.globo.businesscontrol.crosscutting

import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import java.math.BigDecimal
import java.util.*

open class TransacaoDTO(
    val data: Date,
    val valor: BigDecimal,
    val descricao: String,
    val idResponsavel: Long,
    val idConta: Long,
    val tipoTransacao: TipoTransacaoEnum
)

class TransferenciaDTO(val idContaDestino: Long,
                       data: Date,
                       valor: BigDecimal,
                       descricao: String,
                       idResponsavel: Long,
                       idConta: Long) :
    TransacaoDTO(data, valor, descricao, idResponsavel, idConta, TipoTransacaoEnum.TRANSFERENCIA)

class DespesaDTO(val categoriaDespesa: CategoriaDespesaEnum,
                 data: Date,
                 valor: BigDecimal,
                 descricao: String,
                 idResponsavel: Long,
                 idConta: Long) :
    TransacaoDTO(data, valor, descricao, idResponsavel, idConta, TipoTransacaoEnum.DESPESA)

class ReceitaDTO(val categoriaReceita: CategoriaReceitaEnum,
                 data: Date,
                 valor: BigDecimal,
                 descricao: String,
                 idResponsavel: Long,
                 idConta: Long) :
    TransacaoDTO(data, valor, descricao, idResponsavel, idConta, TipoTransacaoEnum.RECEITA)