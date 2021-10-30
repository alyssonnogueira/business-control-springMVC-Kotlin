package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ResponsavelAtualizadoDTO
import com.globo.businesscontrol.crosscutting.ResponsavelDTO

interface IResponsavelService {
    fun criarResponsavel(responsavelDTO: ResponsavelDTO)
    fun atualizarResponsavel(responsavelAtualizadoDTO: ResponsavelAtualizadoDTO)
    fun excluirResponsavel(id: Long)
}