package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Responsavel

interface IResponsavelConsultaService {
    fun obterTodosOsResponsaveis(): List<Responsavel>
    fun obterResponsavel(idResponsavel: Long): Responsavel
}