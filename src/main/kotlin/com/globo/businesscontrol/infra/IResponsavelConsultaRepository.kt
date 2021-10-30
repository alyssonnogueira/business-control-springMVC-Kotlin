package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Responsavel

interface IResponsavelConsultaRepository {
    fun obterResponsavel(idResponsavel: Long): Responsavel
    fun obterTodosOsResponsaveis(): List<Responsavel>
}