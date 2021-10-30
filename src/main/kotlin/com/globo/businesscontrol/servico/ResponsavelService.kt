package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ResponsavelAtualizadoDTO
import com.globo.businesscontrol.crosscutting.ResponsavelDTO
import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import com.globo.businesscontrol.infra.IResponsavelRepository
import org.springframework.stereotype.Service

@Service
class ResponsavelService(
    val responsavelRepository: IResponsavelRepository,
    val responsavelConsultaRepository: IResponsavelConsultaRepository
) : IResponsavelService {

    override fun criarResponsavel(responsavelDTO: ResponsavelDTO) {
        val responsavel = Responsavel(responsavelDTO.nome)
        responsavelRepository.salvarResponsavel(responsavel)
    }

    override fun atualizarResponsavel(responsavelAtualizadoDTO: ResponsavelAtualizadoDTO) {
        val responsavel: Responsavel = responsavelConsultaRepository.obterResponsavel(responsavelAtualizadoDTO.id)
        responsavel.nome = responsavelAtualizadoDTO.nome
        responsavelRepository.salvarResponsavel(responsavel)
    }

    override fun excluirResponsavel(id: Long) {
        responsavelRepository.excluirResponsavel(id)
    }
}