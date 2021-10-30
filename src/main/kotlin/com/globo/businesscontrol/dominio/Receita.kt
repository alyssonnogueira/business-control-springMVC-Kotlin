package com.globo.businesscontrol.dominio

import com.globo.businesscontrol.dominio.enumeradores.CategoriaReceitaEnum
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@DiscriminatorValue("RECEITA")
class Receita(
    data: Date,
    valor: BigDecimal,
    descricao: String,
    responsavel: Responsavel,
    conta: Conta,
    @Enumerated(EnumType.STRING) @Column(
        name = "CATEGORIA_RECEITA",
        nullable = false
    ) val categoriaReceita: CategoriaReceitaEnum
) : Transacao(data, valor, descricao, responsavel, conta, TipoTransacaoEnum.RECEITA) {

    override fun excluirTransacao() {
        debitarSaldo(this.conta)
        super.excluirTransacao()
    }

    init {
        creditarSaldo(this.conta)
    }
}