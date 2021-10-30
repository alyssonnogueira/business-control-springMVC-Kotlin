package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Conta

interface IContaConsultaService {
    fun obterTodasAsContas(): List<Conta>
    fun obterConta(idConta: Long): Conta
}