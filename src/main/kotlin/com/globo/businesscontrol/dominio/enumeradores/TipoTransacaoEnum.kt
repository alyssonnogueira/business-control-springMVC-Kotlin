package com.globo.businesscontrol.dominio.enumeradores

enum class TipoTransacaoEnum {
    DESPESA, RECEITA, TRANSFERENCIA;

    val isTransferencia: Boolean
        get() = this == TRANSFERENCIA
}