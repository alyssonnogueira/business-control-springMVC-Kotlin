package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.ContaAtualizadaDTO
import com.globo.businesscontrol.crosscutting.ContaDTO
import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.infra.IContaConsultaRepository
import com.globo.businesscontrol.infra.IContaRepository
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import org.springframework.stereotype.Service

@Service
class ContaService(
    val contaRepository: IContaRepository,
    val contaConsultaRepository: IContaConsultaRepository,
    val responsavelConsultaRepository: IResponsavelConsultaRepository
) : IContaService {

    override fun criarConta(contaDTO: ContaDTO) {
        val responsavel: Responsavel = responsavelConsultaRepository.obterResponsavel(contaDTO.idResponsavel)
        val conta = Conta(contaDTO.nome, contaDTO.saldoInicial, contaDTO.tipoConta, responsavel)
        contaRepository.save(conta)
    }

    override fun atualizarConta(contaAtualizadaDTO: ContaAtualizadaDTO) {
        val conta: Conta = contaConsultaRepository.obterConta(contaAtualizadaDTO.id)
        conta.nome = contaAtualizadaDTO.nome
        contaRepository.save(conta)
    }

    override fun excluirConta(contaId: Long) {
        contaRepository.deleteById(contaId)
    }
}