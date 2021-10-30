package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ContaAtualizadaDTO
import com.globo.businesscontrol.crosscutting.ContaDTO

interface IContaService {
    fun criarConta(contaDTO: ContaDTO)
    fun atualizarConta(contaAtualizadaDTO: ContaAtualizadaDTO)
    fun excluirConta(contaId: Long)
}