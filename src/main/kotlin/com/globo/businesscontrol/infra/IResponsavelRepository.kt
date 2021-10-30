package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Responsavel

interface IResponsavelRepository {

    fun salvarResponsavel(responsavel: Responsavel)

    fun excluirResponsavel(idResponsavel: Long)

}