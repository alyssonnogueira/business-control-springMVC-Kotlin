package com.globo.businesscontrol.servico

import com.globo.businesscontrol.crosscutting.DespesaDTO
import com.globo.businesscontrol.crosscutting.ReceitaDTO
import com.globo.businesscontrol.crosscutting.TransferenciaDTO
import com.globo.businesscontrol.dominio.*
import com.globo.businesscontrol.infra.IContaConsultaRepository
import com.globo.businesscontrol.infra.IResponsavelConsultaRepository
import com.globo.businesscontrol.infra.ITransacaoConsultaRepository
import com.globo.businesscontrol.infra.ITransacaoRepository
import org.springframework.stereotype.Service

@Service
class TransacaoService(
    val transacaoRepository: ITransacaoRepository,
    val transacaoConsultaRepository: ITransacaoConsultaRepository,
    val responsavelConsultaRepository: IResponsavelConsultaRepository,
    val contaConsultaRepository: IContaConsultaRepository
) : ITransacaoService {

    override fun criarTransferencia(despesaDTO: DespesaDTO) {
        val responsavel: Responsavel = responsavelConsultaRepository.obterResponsavel(despesaDTO.idResponsavel)
        val conta: Conta = contaConsultaRepository.obterConta(despesaDTO.idConta)
        val despesa: Transacao = Despesa(
            despesaDTO.data,
            despesaDTO.valor,
            despesaDTO.descricao,
            responsavel,
            conta,
            despesaDTO.categoriaDespesa
        )
        transacaoRepository.save(despesa)
    }

    override fun criarTransferencia(receitaDTO: ReceitaDTO) {
        val responsavel: Responsavel = responsavelConsultaRepository.obterResponsavel(receitaDTO.idResponsavel)
        val conta: Conta = contaConsultaRepository.obterConta(receitaDTO.idConta)
        val receita: Transacao = Receita(
            receitaDTO.data,
            receitaDTO.valor,
            receitaDTO.descricao,
            responsavel,
            conta,
            receitaDTO.categoriaReceita
        )
        transacaoRepository.save(receita)
    }

    override fun criarTransferencia(transferenciaDTO: TransferenciaDTO) {
        val responsavel: Responsavel =
            responsavelConsultaRepository.obterResponsavel(transferenciaDTO.idResponsavel)
        val conta: Conta = contaConsultaRepository.obterConta(transferenciaDTO.idConta)
        val contaDestino: Conta = contaConsultaRepository.obterConta(transferenciaDTO.idContaDestino)
        val transferencia: Transacao = Transferencia(
            transferenciaDTO.data,
            transferenciaDTO.valor,
            transferenciaDTO.descricao,
            responsavel,
            conta,
            contaDestino
        )
        transacaoRepository.save(transferencia)
    }

    override fun excluirTransacao(transacaoId: Long) {
        val transacao: Transacao = transacaoConsultaRepository.obterTransacao(transacaoId)
        transacao.excluirTransacao()
        transacaoRepository.save(transacao)
    }

}