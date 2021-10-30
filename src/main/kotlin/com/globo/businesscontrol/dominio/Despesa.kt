package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dominio.enumeradores.CategoriaDespesaEnum
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@DiscriminatorValue("DESPESA")
class Despesa(
    data: Date,
    valor: BigDecimal,
    descricao: String,
    responsavel: Responsavel,
    conta: Conta,
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA_DESPESA", nullable = false)
    val categoriaDespesa: CategoriaDespesaEnum
) : Transacao(data, valor, descricao, responsavel, conta, TipoTransacaoEnum.DESPESA) {

    override fun excluirTransacao() {
        creditarSaldo(this.conta)
        super.excluirTransacao()
    }

    init {
        debitarSaldo(this.conta)
    }
}