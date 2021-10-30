package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import org.springframework.stereotype.Service

@Service
class ResponsavelConsultaService(val responsavelConsultaRepository: IResponsavelConsultaRepository) :
    IResponsavelConsultaService {

    override fun obterTodosOsResponsaveis(): List<Responsavel> =
        responsavelConsultaRepository.obterTodosOsResponsaveis()

    override fun obterResponsavel(idResponsavel: Long): Responsavel =
        responsavelConsultaRepository.obterResponsavel(idResponsavel)

}