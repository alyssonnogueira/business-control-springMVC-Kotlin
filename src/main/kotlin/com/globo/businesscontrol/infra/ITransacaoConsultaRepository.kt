package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Transacao
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ITransacaoConsultaRepository {
    fun obterTransacao(idTransacao: Long?): Transacao
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
}