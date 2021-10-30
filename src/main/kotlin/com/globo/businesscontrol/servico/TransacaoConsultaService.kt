package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Transacao
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import com.globo.businesscontrol.infra.ITransacaoConsultaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TransacaoConsultaService(val transacaoConsultaRepository: ITransacaoConsultaRepository) :
    ITransacaoConsultaService {

    override fun obterTodasTransacoes(): List<Transacao> = transacaoConsultaRepository.obterTodasTransacoes()

    override fun obterTodasTransacoes(
        descricaoCategoria: String?,
        idsResponsaveis: List<Long>?,
        idsContas: List<Long>?,
        tiposTransacao: List<TipoTransacaoEnum>?,
        dataInicial: LocalDateTime?,
        dataFinal: LocalDateTime?,
        pageable: Pageable
    ): Page<Transacao> = transacaoConsultaRepository.obterTodasTransacoes(
        descricaoCategoria,
        idsResponsaveis,
        idsContas,
        tiposTransacao,
        dataInicial,
        dataFinal,
        pageable
    )

    override fun obterTransacao(idTransacao: Long): Transacao = transacaoConsultaRepository.obterTransacao(idTransacao)
}