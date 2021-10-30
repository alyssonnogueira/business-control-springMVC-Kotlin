package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.infra.IContaConsultaRepository
import org.springframework.stereotype.Service

@Service
class ContaConsultaService(val contaConsultaRepository: IContaConsultaRepository) : IContaConsultaService {

    override fun obterTodasAsContas(): List<Conta> = contaConsultaRepository.obterTodasAsContas()

    override fun obterConta(idConta: Long): Conta = contaConsultaRepository.obterConta(idConta)

}