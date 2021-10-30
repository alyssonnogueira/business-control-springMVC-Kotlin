package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import com.globo.businesscontrol.dominio.eventos.CreditarSaldoEvent
import com.globo.businesscontrol.dominio.eventos.DebitarSaldoEvent
import com.globo.businesscontrol.dominio.eventos.MudarSaldoEvent
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.DomainEvents
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TRANSACAO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_TRANSACAO", discriminatorType = DiscriminatorType.STRING)
@Where(clause = "DATA_EXCLUSAO IS NULL")
@SQLDelete(sql = "UPDATE TRANSACAO SET DATA_EXCLUSAO=CURRENT_TIMESTAMP WHERE ID=?")
abstract class Transacao protected constructor(
    data: Date,
    @Column(name = "VALOR", nullable = false) val valor: BigDecimal,
    @Column(name = "DESCRICAO", nullable = false) val descricao: String,
    @ManyToOne @JoinColumn(name = "ID_RESPONSAVEL", nullable = false) val responsavel: Responsavel,
    @ManyToOne @JoinColumn(name = "ID_CONTA", nullable = false) val conta: Conta,
    @Enumerated(EnumType.STRING) @Column(
        name = "TIPO_TRANSACAO",
        nullable = false,
        insertable = false,
        updatable = false
    ) val tipoTransacao: TipoTransacaoEnum
) {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id: Long? = null
        private set

    @CreatedDate
    @Column(name = "DATA_TRANSACAO", nullable = false)
    val data: LocalDateTime = LocalDateTime.ofInstant(data.toInstant(), ZoneId.of("America/Sao_Paulo"));

    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now()

    @Column(name = "DATA_EXCLUSAO")
    var dataExclusao: LocalDateTime? = null
        private set

    @Transient
    var events: MutableList<MudarSaldoEvent>? = mutableListOf()

    protected fun debitarSaldo(conta: Conta) {
        val debitarEvent = DebitarSaldoEvent(conta, this.valor)
        if (events != null) events?.add(debitarEvent) else events = mutableListOf(debitarEvent)
    }

    protected fun creditarSaldo(conta: Conta) {
        val creditarEvent = CreditarSaldoEvent(conta, this.valor)
        if (events != null) events?.add(creditarEvent) else events = mutableListOf(creditarEvent)
    }

    @DomainEvents
    fun eventoAlterarSaldoConta(): Collection<MudarSaldoEvent> {
        return events ?: mutableListOf()
    }

    open fun excluirTransacao() {
        dataExclusao = LocalDateTime.now()
    }
}