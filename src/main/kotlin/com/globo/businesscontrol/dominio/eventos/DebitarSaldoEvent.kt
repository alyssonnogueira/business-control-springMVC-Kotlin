package com.globo.businesscontrol.dominio.eventos

import com.globo.businesscontrol.dominio.Conta
import java.math.BigDecimal

class DebitarSaldoEvent(override val conta: Conta, override val valor: BigDecimal) : MudarSaldoEvent