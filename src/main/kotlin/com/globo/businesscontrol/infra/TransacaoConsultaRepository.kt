package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Transacao
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Repository
class TransacaoConsultaRepository(private val transacaoJpaRepository: TransacaoJpaRepository) :
    ITransacaoConsultaRepository {
    override fun obterTransacao(idTransacao: Long?): Transacao {
        return transacaoJpaRepository
            .findByIdOrNull(idTransacao) ?: throw EntityNotFoundException("Transação não encontrada")
    }

    override fun obterTodasTransacoes(): List<Transacao> = transacaoJpaRepository.findAll()

    override fun obterTodasTransacoes(
        descricaoCategoria: String?,
        idsResponsaveis: List<Long>?,
        idsContas: List<Long>?,
        tiposTransacao: List<TipoTransacaoEnum>?,
        dataInicial: LocalDateTime?,
        dataFinal: LocalDateTime?,
        pageable: Pageable
    ): Page<Transacao> = transacaoJpaRepository.findAllPageAndFilter(
        descricaoCategoria,
        idsResponsaveis,
        idsContas,
        tiposTransacao,
        dataInicial,
        dataFinal,
        pageable
    )

    interface TransacaoJpaRepository : JpaRepository<Transacao, Long> {
        @Query(
            "SELECT t FROM Transacao as t "
                    + "WHERE "
                    + "(UPPER(t.descricao) LIKE UPPER(CONCAT('%', :descricaoCategoria, '%')) OR :descricaoCategoria IS NULL) AND "
                    + "(CONCAT(:idsResponsaveis) IS NULL OR t.responsavel.id in :idsResponsaveis) AND "
                    + "(CONCAT(:idsContas) IS NULL OR t.conta.id in :idsContas) AND "
                    + "(CONCAT(:tiposTransacao) IS NULL OR t.tipoTransacao in :tiposTransacao) AND "
                    + "(:dataInicial IS NULL OR t.data >= :dataInicial) AND "
                    + "(:dataFinal IS NULL OR t.data <= :dataFinal)"
        )
        fun findAllPageAndFilter(
            @Param("descricaoCategoria") descricaoCategoria: String?,
            @Param("idsResponsaveis") idsResponsaveis: List<Long>?,
            @Param("idsContas") idsContas: List<Long>?,
            @Param("tiposTransacao") tiposTransacao: List<TipoTransacaoEnum>?,
            @Param("dataInicial") dataInicial: LocalDateTime?,
            @Param("dataFinal") dataFinal: LocalDateTime?,
            pageable: Pageable
        ): Page<Transacao>
    }
}