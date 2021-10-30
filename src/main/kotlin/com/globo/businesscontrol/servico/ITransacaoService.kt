package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.DespesaDTO
import com.globo.businesscontrol.crosscutting.ReceitaDTO
import com.globo.businesscontrol.crosscutting.TransferenciaDTO

interface ITransacaoService {
    fun criarTransferencia(despesaDTO: DespesaDTO)
    fun criarTransferencia(receitaDTO: ReceitaDTO)
    fun criarTransferencia(transferenciaDTO: TransferenciaDTO)
    fun excluirTransacao(transacaoId: Long)
}