package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Transacao
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface ITransacaoConsultaService {
    fun obterTodasTransacoes(): List<Transacao>
    fun obterTodasTransacoes(
        descricaoCategoria: String?,
        idsResponsaveis: List<Long>?,
        idsContas: List<Long>?,
        tiposTransacao: List<TipoTransacaoEnum>?,
        dataInicial: LocalDateTime?,
        dataFinal: LocalDateTime?,
        pageable: Pageable
    ): Page<Transacao>

    fun obterTransacao(idTransacao: Long): Transacao
}