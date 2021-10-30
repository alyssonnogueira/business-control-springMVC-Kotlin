package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "CONTA")
@Where(clause = "DATA_EXCLUSAO IS NULL")
@SQLDelete(sql = "UPDATE CONTA SET DATA_EXCLUSAO=CURRENT_TIMESTAMP WHERE ID=?")
class Conta(
    nome: String,
    @Column(name = "SALDO_INICIAL", nullable = false) val saldoInicial: BigDecimal,
    @Enumerated(EnumType.STRING) @Column(name = "TIPO_CONTA", nullable = false) val tipoConta: TipoContaEnum,
    @ManyToOne @JoinColumn(name = "ID_RESPONSAVEL", nullable = false) val responsavel: Responsavel
) {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id: Long? = null
        private set

    @Column(name = "NOME", nullable = false)
    var nome: String = nome
        set(value) {
            field = value
            this.dataAtualizacao = LocalDateTime.now()
        }

    @Column(name = "SALDO", nullable = false)
    var saldo: BigDecimal = saldoInicial
        private set

    @CreatedDate
    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    val dataCriacao: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    var dataAtualizacao: LocalDateTime = LocalDateTime.now()
        private set

    @Column(name = "DATA_EXCLUSAO")
    var dataExclusao: LocalDateTime? = null
        private set

    fun creditarSaldo(valor: BigDecimal) {
        saldo = saldo.add(valor)
    }

    fun debitarSaldo(valor: BigDecimal) {
        saldo = saldo.subtract(valor)
    }

}