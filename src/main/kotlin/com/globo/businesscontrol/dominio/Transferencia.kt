package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import java.math.BigDecimal
import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@DiscriminatorValue("TRANSFERENCIA")
class Transferencia(
    data: Date,
    valor: BigDecimal,
    descricao: String,
    responsavel: Responsavel,
    conta: Conta,
    @JoinColumn(
        name = "ID_CONTA_DESTINO",
        nullable = false
    ) @ManyToOne val contaDestino: Conta
) : Transacao(data, valor, descricao, responsavel, conta, TipoTransacaoEnum.TRANSFERENCIA) {

    override fun excluirTransacao() {
        creditarSaldo(this.conta)
        debitarSaldo(this.contaDestino)
        super.excluirTransacao()
    }

    init {
        debitarSaldo(this.conta)
        creditarSaldo(contaDestino)
    }
}