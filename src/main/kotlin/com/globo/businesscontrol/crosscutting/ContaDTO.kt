package com.globo.businesscontrol.crosscutting

import com.globo.businesscontrol.dominio.enumeradores.TipoContaEnum
import java.math.BigDecimal

class ContaDTO(val nome: String, val saldoInicial: BigDecimal, val tipoConta: TipoContaEnum, val idResponsavel: Long)

class ContaAtualizadaDTO(val id: Long, val nome: String)