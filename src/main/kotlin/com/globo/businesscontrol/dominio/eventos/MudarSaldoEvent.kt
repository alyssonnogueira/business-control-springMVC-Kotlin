package com.globo.businesscontrol.dominio.eventos

import com.globo.businesscontrol.dominio.Conta
import java.math.BigDecimal

interface MudarSaldoEvent {
    val conta: Conta
    val valor: BigDecimal
}