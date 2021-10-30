package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Conta

interface IContaConsultaRepository {
    fun obterConta(idConta: Long): Conta
    fun obterTodasAsContas(): List<Conta>
}